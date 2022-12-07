package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.Activity;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface ActivityService {

    // 查询活动列表
    List<Activity> getActivityList();
}
