package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.ApiResponse;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.pojo.Activity;
import cn.edu.gdufs.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@RestController
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityService activityService;

    /**
     * 查询活动列表
     */
    @GetMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public List<Activity> getActivityList() {
        return activityService.getActivityList();
    }

    /**
     * 查询活动详情
     */
    @GetMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public Activity getActivityDetail(@PathVariable long id) {
        return activityService.getActivityById(id);
    }

    /**
     * 新增活动
     */
    @PostMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public Activity insertActivity(@RequestBody @Valid Activity activity) {
        activityService.insertActivity(activity);
        return activity;
    }

    /**
     * 修改活动
     */
    @PutMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public ApiResponse<Object> updateActivity(@RequestBody @Valid Activity activity) {
        if (activity.getId() == null || activity.getId() < 1) {
            return ApiResponse.paramError("id参数错误");
        }
        activityService.updateActivity(activity);
        return ApiResponse.success();
    }

}
