package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.ApiResponse;
import cn.edu.gdufs.common.PageResult;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.pojo.Activity;
import cn.edu.gdufs.service.ActivityService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@RestController
@RequestMapping("/activity")
@Validated
public class ActivityController extends BaseController {

    @Autowired
    private ActivityService activityService;

    /**
     * 查询活动列表
     */
    @GetMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public PageResult<Activity> getActivityList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                @RequestParam(defaultValue = "5") Integer pageSize) {
        PageResult<Activity> result = new PageResult<>();
        BeanUtils.copyProperties(PageInfo.of(activityService.getActivityList(pageNumber, pageSize)), result);
        return result;
    }

    /**
     * 查询活动详情
     */
    @GetMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public Activity getActivityDetail(@Min(value = 1, message = "id不能小于1") @PathVariable long id) {
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

    /**
     * 删除活动
     */
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable long id) {
        activityService.deleteActivity(id);
    }

}
