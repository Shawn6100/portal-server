package cn.edu.gdufs.controller;

import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.util.MD5Util;
import cn.edu.gdufs.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Admin admin = adminService.login(username, password);

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

    /**
     * 管理员修改密码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    @PutMapping("/password")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public void updatePassword(String oldPassword, String newPassword) {
        if (newPassword == null || "".equals(newPassword)) {
            throw new ApiException("密码不能为空");
        }
        Admin admin = adminService.getAdminById(getUserId());
        // 判断原密码是否正确
        if (adminService.checkPassword(admin, oldPassword)) {
            throw new ApiException("原密码输入错误");
        }
        // 修改密码
        adminService.updatePassword(getUserId(), MD5Util.encode(newPassword.trim(), admin.getSalt()));
        // 退出登录
        adminLogout();
    }

}
