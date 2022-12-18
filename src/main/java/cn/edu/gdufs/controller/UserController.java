package cn.edu.gdufs.controller;

import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.UserInsertDTO;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.service.UserService;
import cn.edu.gdufs.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String login(@NotBlank(message = "邮箱不能为空") String email,
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
}
