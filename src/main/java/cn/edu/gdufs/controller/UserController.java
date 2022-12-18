package cn.edu.gdufs.controller;

import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.UserInsertDTO;
import cn.edu.gdufs.controller.dto.UserUpdateDTO;
import cn.edu.gdufs.controller.vo.UserInfoVO;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.service.UserService;
import cn.edu.gdufs.util.MD5Util;
import cn.edu.gdufs.util.TokenUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/18
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public void register(@RequestBody @Valid UserInsertDTO userInsertDTO) {
        // 数据模型转换
        User user = new User();
        BeanUtils.copyProperties(userInsertDTO, user);

        // 用户注册
        userService.register(user, userInsertDTO.getVerificationCode());
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public String login(@NotBlank(message = "邮箱不能为空") @Email(message = "邮箱格式错误") String email,
                        @NotBlank(message = "密码不能为空") String password) {
        User user = userService.login(email, password);
        // 发放token
        return tokenUtil.grantToken(user.getId(), user.getRole());
    }

    /**
     * 用户登出
     */
    @GetMapping("/logout")
    @RequiredPermission(RoleConstant.ROLE_USER)
    public void logout() {
        tokenUtil.deleteToken(getUserId());
    }

    /**
     * 获取个人信息
     */
    @GetMapping("/info")
    @RequiredPermission(RoleConstant.ROLE_USER)
    public UserInfoVO getUserInfo() {
        User user = userService.getUserById(getUserId());

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);

        return userInfoVO;
    }

    /**
     * 修改个人信息
     */
    @PutMapping("/info")
    @RequiredPermission(RoleConstant.ROLE_USER)
    public void updateUserInfo(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        // 数据模型转换
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO, user);

        // 更新用户信息
        userService.updateUser(user);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    @RequiredPermission(RoleConstant.ROLE_USER)
    public void changePassword(@NotBlank(message = "原密码不能为空") String oldPassword,
                               @Length(min = 6, message = "密码长度不能小于6位")
                               @NotBlank(message = "密码不能为空") String newPassword) {
        // 获取用户信息
        User user = userService.getUserById(getUserId());

        // 判断原密码是否正确
        if (!userService.checkPassword(user, oldPassword)) {
            throw new ApiException("原密码输入错误");
        }

        // 修改密码
        userService.updatePassword(getUserId(), MD5Util.encode(newPassword, user.getSalt()));

        // 退出登录，删除token
        logout();
    }
}
