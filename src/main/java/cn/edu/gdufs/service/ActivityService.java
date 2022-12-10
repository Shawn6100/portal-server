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
    List<Activity> getActivityList(int pageNumber, int pageSize);

    // 根据id查询活动信息
    Activity getActivityById(long id);

    // 新增活动
    void insertActivity(Activity activity);

    // 修改活动
    void updateActivity(Activity activity);

    // 删除活动
    void deleteActivity(long id);
}
