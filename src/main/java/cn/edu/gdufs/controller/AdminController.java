package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.ApiResponse;
import cn.edu.gdufs.common.PageResult;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.AdminInsertDTO;
import cn.edu.gdufs.controller.dto.AdminUpdateDTO;
import cn.edu.gdufs.controller.vo.AdminDetailVO;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.util.MD5Util;
import cn.edu.gdufs.util.TokenUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
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
     * 查询管理员列表
     * @return 管理员信息列表
     */
    @GetMapping()
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public PageResult<Admin> getAdminList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                        @RequestParam(defaultValue = "5") Integer pageSize) {
        // 封装分页结果
        PageResult<Admin> result = new PageResult<>();
        BeanUtils.copyProperties(PageInfo.of(adminService.getAdminList(pageNumber, pageSize)), result);

        return result;
    }

    /**
     * 查询管理员信息详情
     * @param id 管理员id
     * @return 管理员信息
     */
    @GetMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public AdminDetailVO getAdminDetail(@PathVariable long id) {
        return adminService.getAdminDetail(id);
    }

    /**
     * 超级管理员添加管理员
     * @param adminInsertDTO AdminInsertDTO
     * @return AdminDetailVO
     */
    @PostMapping()
    @RequiredPermission(RoleConstant.ROLE_SUPER_ADMIN)
    public AdminDetailVO insertAdmin(@RequestBody @Valid AdminInsertDTO adminInsertDTO) {
        // 数据模型转换
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminInsertDTO, admin);

        // 新增管理员
        admin = adminService.insertAdmin(admin);

        // 数据模型转换
        AdminDetailVO adminDetailVO = new AdminDetailVO();
        BeanUtils.copyProperties(admin, adminDetailVO);
        return adminDetailVO;
    }

    /**
     * 管理员修改个人信息
     * @param adminUpdateDTO AdminUpdateDTO
     * @return ApiResponse<Object>
     */
    @PutMapping()
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public ApiResponse<Object> editProfile(@RequestBody @Valid AdminUpdateDTO adminUpdateDTO) {
        // 普通管理员只能修改自己的信息
        if (getUserRole() == RoleConstant.ROLE_NORMAL_ADMIN && getUserId() != adminUpdateDTO.getId()) {
            return ApiResponse.permissionError();
        }

        // 数据模型转换
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminUpdateDTO, admin);

        // 修改管理员信息
        adminService.updateAdmin(admin);
        return ApiResponse.success();
    }

    /**
     * 超级管理员删除管理员
     * @param id 管理员id
     * @return ApiResponse<Object>
     */
    @DeleteMapping("/{id}")
    @RequiredPermission(RoleConstant.ROLE_SUPER_ADMIN)
    public ApiResponse<Object> deleteAdmin(@PathVariable long id) {
        if (id <= 0) {
            return ApiResponse.paramError("管理员id不能小于1");
        }
        // 防止超级管理员删除自己
        if (id == getUserId()) {
            return ApiResponse.fail("非法操作");
        }
        adminService.deleteAdmin(id);
        return ApiResponse.success();
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
        // 删除token，重新登录
        adminLogout();
    }

}
