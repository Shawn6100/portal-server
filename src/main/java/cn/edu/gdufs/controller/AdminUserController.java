package cn.edu.gdufs.controller;

import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.vo.UserForAdminVO;
import cn.edu.gdufs.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 管理员管理用户控制器
 * Author: 程诗怡
 * Date: 2022/12/19
 */
@RestController
@RequestMapping("/admin/user")
@RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
@Validated
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 查看用户列表
     */
    @GetMapping
    public List<UserForAdminVO> getUserList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        return adminUserService.getUserList(pageNumber, pageSize);
    }
}
