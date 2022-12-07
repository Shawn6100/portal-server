package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.mapper.ActivityMapper;
import cn.edu.gdufs.pojo.Activity;
import cn.edu.gdufs.service.ActivityService;
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

    @Override
    public List<Activity> getActivityList() {
        return activityMapper.getActivityList();
    }
}
