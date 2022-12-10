package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.mapper.ActivityMapper;
import cn.edu.gdufs.pojo.Activity;
import cn.edu.gdufs.service.ActivityService;
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

    @Override
    public List<Activity> getActivityList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return activityMapper.getActivityList();
    }

    @Override
    public Activity getActivityById(long id) {
        return activityMapper.getActivityById(id);
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
