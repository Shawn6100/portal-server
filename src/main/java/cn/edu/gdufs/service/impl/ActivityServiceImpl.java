package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.ActivityMapper;
import cn.edu.gdufs.pojo.Activity;
import cn.edu.gdufs.service.ActivityService;
import cn.edu.gdufs.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Author: 冯秋玲
 * Date: 2022/12/7
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Activity> getActivityList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return activityMapper.getActivityList();
    }

    @Override
    public Activity getActivityById(long id) {
        // 先查询 Redis 中是否有记录
        String key = String.format(CacheConstant.ACTIVITY_INFO, id);
        String activityString = (String) redisUtil.get(key);
        if (activityString != null) {
            return JSONObject.parseObject(activityString, Activity.class);
        }

        // Redis 中查询不到，再查询 MySQL
        Activity activity = activityMapper.getActivityById(id);
        if (activity == null) {
            throw new ApiException("活动id参数错误，该活动不存在");
        }

        // 存入 Redis 中
        redisUtil.set(key, JSON.toJSONString(activity), CacheConstant.EXPIRE_TIME);

        return activity;
    }

    @Override
    public void insertActivity(Activity activity) {
        activityMapper.insertActivity(activity);
    }

    @Override
    public void updateActivity(Activity activity) {
        activityMapper.updateActivity(activity);
    }

    @Override
    public void deleteActivity(long id) {
        activityMapper.deleteActivity(id);
    }
}
