package cn.edu.gdufs.controller;

import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 管理员登录接口
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    @PostMapping("/login")
    public String adminLogin(String username, String password) {
        // 校验用户名和密码
        Admin admin = adminService.checkPassword(username, password);

        // 发放token
        return tokenUtil.grantToken(admin.getId(), admin.getRole());
    }

    /**
     * 管理员登出接口
     */
    @GetMapping("/logout")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public void adminLogout() {
        // 删除用户token
        tokenUtil.deleteToken(getUserId());
    }

}
