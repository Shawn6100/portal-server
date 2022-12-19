package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.LectureMapper;
import cn.edu.gdufs.pojo.Lecture;
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
}
