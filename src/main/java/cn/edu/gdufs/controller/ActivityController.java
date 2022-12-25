package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.PageResult;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.ActivityInsertDTO;
import cn.edu.gdufs.controller.dto.ActivityUpdateDTO;
import cn.edu.gdufs.controller.vo.ActivityFrontVO;
import cn.edu.gdufs.pojo.Activity;
import cn.edu.gdufs.service.ActivityService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

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
     *
     * @param pageNumber 页码
     * @param pageSize   页面大小
     * @return 分页活动列表
     */
    @GetMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public PageResult<Activity> getActivityList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                @RequestParam(defaultValue = "5") Integer pageSize) {
        PageResult<Activity> result = new PageResult<>();
        BeanUtils.copyProperties(PageInfo.of(activityService.getActivityListByPage(pageNumber, pageSize)), result);
        return result;
    }

    /**
     * 查询活动详情
     *
     * @param id 活动id
     * @return 活动详情
     */
    @GetMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public Activity getActivityDetail(@Min(value = 1, message = "id不能小于1") @PathVariable long id) {
        return activityService.getActivityById(id);
    }

    /**
     * 新增活动
     *
     * @param activityInsertDTO 新增活动DTO
     * @return 新增活动信息
     */
    @PostMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public Activity insertActivity(@RequestBody @Valid ActivityInsertDTO activityInsertDTO) {
        // 数据模型转换
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityInsertDTO, activity);

        // 新增活动
        activityService.insertActivity(activity);
        return activity;
    }

    /**
     * 修改活动
     *
     * @param activityUpdateDTO 修改活动DTO
     */
    @PutMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public void updateActivity(@RequestBody @Valid ActivityUpdateDTO activityUpdateDTO) {
        // 数据模型转换
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityUpdateDTO, activity);

        // 修改活动
        activityService.updateActivity(activity);
    }

    /**
     * 删除活动
     *
     * @param id 活动id
     */
    @DeleteMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public void deleteActivity(@Min(value = 1, message = "id不能小于1") @PathVariable long id) {
        activityService.deleteActivity(id);
    }

    /**
     * 前台查询活动列表
     */
    @GetMapping("/front")
    public List<ActivityFrontVO> getFrontActivityList() {
        // 获取活动列表数据
        List<Activity> activityList = activityService.getActivityList();

        // 数据模型转换
        List<ActivityFrontVO> result = new ArrayList<>();
        for (Activity activity : activityList) {
            ActivityFrontVO activityFrontVO = new ActivityFrontVO();
            BeanUtils.copyProperties(activity, activityFrontVO);
            result.add(activityFrontVO);
        }

        return result;
    }

}
