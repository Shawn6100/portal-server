package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.constant.LectureConstant;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.LectureMapper;
import cn.edu.gdufs.mapper.LectureMemberMapper;
import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.pojo.LectureMember;
import cn.edu.gdufs.service.LectureService;
import cn.edu.gdufs.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private LectureMemberMapper lectureMemberMapper;

    @Autowired
    private RedisUtil redisUtil;

    // 分页查询分享会列表
    @Override
    public List<Lecture> getLectureList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return lectureMapper.getLectureList();
    }

    // 查询分享会详情
    @Override
    public Lecture getLectureById(long id) {
        // 查询Redis中是否存在记录
        String key = String.format(CacheConstant.LECTURE_INFO, id);
        String lectureString = (String) redisUtil.get(key);
        if (lectureString != null) {
            // 缓存穿透假值处理
            if ("".equals(lectureString)) {
                throw new ApiException("分享会不存在");
            }
            return JSONObject.parseObject(lectureString, Lecture.class);
        }

        // 获取分享会信息
        Lecture lecture = lectureMapper.getLectureById(id);

        // MySQL中未查到数据
        if (lecture == null) {
            // 防止缓存穿透处理
            redisUtil.set(key, "", CacheConstant.SHORT_EXPIRE_TIME);
            throw new ApiException("分享会不存在");
        }

        // 存入Redis
        redisUtil.set(key, JSON.toJSONString(lecture), CacheConstant.EXPIRE_TIME);

        return lectureMapper.getLectureById(id);
    }

    // 新增分享会信息
    @Override
    public void insertLecture(Lecture lecture) {
        lectureMapper.insertLecture(lecture);

        // 将报名容量存入Redis
        String capacityKey = String.format(CacheConstant.LECTURE_SIGNUP_REMAINING_CAPACITY, lecture.getId());
        redisUtil.set(capacityKey, lecture.getCapacity());
    }

    // 修改分享会信息
    @Override
    @Transactional
    public void updateLecture(Lecture lecture) {
        // 修改MySQL中的数据
        lectureMapper.updateLecture(lecture);

        // 删除Redis中的数据
        redisUtil.del(String.format(CacheConstant.LECTURE_INFO, lecture.getId()));
    }

    // 删除分享会信息
    @Override
    @Transactional
    public void deleteLecture(long id) {
        // 删除MySQL中的数据
        lectureMapper.deleteLecture(id);

        // 删除Redis中的数据
        redisUtil.del(String.format(CacheConstant.LECTURE_INFO, id));
    }

    // 用户报名分享会
    @Override
    @Transactional
    public void signUpLecture(long userId, long lectureId) {
        // 查询分享会信息
        Lecture lecture = getLectureById(lectureId);
        if (lecture == null) {
            throw new ApiException("分享会不存在");
        }
        if (LectureConstant.LECTURE_NOT_ALLOW_SIGN_UP == lecture.getStatus()) {
            throw new ApiException("该分享会停止报名");
        }

        // 检查是否已取消报名
        if (lectureMemberMapper.checkIsCanceled(userId, lectureId) != null) {
            throw new ApiException("您已取消报名该活动，无法再次报名");
        }

        // 报名
        String capacityKey = String.format(CacheConstant.LECTURE_SIGNUP_REMAINING_CAPACITY, lectureId);
        String userListKey = String.format(CacheConstant.LECTURE_SIGNUP_USER_LIST, lectureId);
        try {
            int result = redisUtil.executeSignupScript(capacityKey, userListKey, userId);
            // 判断报名结果
            switch (result) {
                case LectureConstant.LUA_SIGNUP_CAPACITY_IS_FULL:
                    throw new ApiException("报名人数已满");
                case LectureConstant.LUA_SIGNUP_REPEAT:
                    throw new ApiException("请不要重复报名");
                // 报名成功
                case LectureConstant.LUA_SIGNUP_SUCCESS:
                    // 写入MySQL
                    LectureMember lectureMember = new LectureMember(userId, lectureId);
                    lectureMemberMapper.insertLectureMember(lectureMember);
                    break;
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e){
            // 出现异常时回滚，报名容量加1，已报名列表移除该用户
            redisUtil.incr(capacityKey);
            redisUtil.lRem(userListKey, userId);
            e.printStackTrace();
            throw new ApiException("报名失败，请重试");
        }
    }

    // 用户取消报名分享会
    @Override
    @Transactional
    public void cancelSignUpLecture(long userId, long lectureId) {

        // 查询分享会信息
        Lecture lecture = getLectureById(lectureId);
        if (lecture == null) {
            throw new ApiException("分享会不存在");
        }
        if (LectureConstant.LECTURE_NOT_ALLOW_SIGN_UP == lecture.getStatus()) {
            throw new ApiException("该分享会报名停止");
        }

        // 检查用户是否报名该分享会
        LectureMember lectureMember = lectureMemberMapper.getLectureMemberByUserIdAndLectureId(userId, lectureId);
        if (lectureMember == null) {
            throw new ApiException("您未报名该分享会");
        }

        // 剩余报名容量key
        String capacityKey = String.format(CacheConstant.LECTURE_SIGNUP_REMAINING_CAPACITY, lectureId);
        try {
            // 删除MySQL中的记录
            lectureMemberMapper.cancelSignup(userId, lectureId);
            // Redis中剩余报名容量加一
            redisUtil.incr(capacityKey);
        } catch (Exception e) {
            // 出现异常，恢复报名容量
            redisUtil.decr(capacityKey);
        }
    }

    // 用户参与的分享会列表
    @Override
    public List<Lecture> getUserAttendLectureList(long userId, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return lectureMapper.getUserAttendLectureList(userId);
    }
}
