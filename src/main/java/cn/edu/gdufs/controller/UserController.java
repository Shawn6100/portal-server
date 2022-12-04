package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.ApiResponse;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.service.UserService;
import cn.edu.gdufs.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/login")
    public String adminLogin(String username, String password) {
        // 校验用户名和密码
        User user = userService.checkPassword(username, password);

        // 发放token
        return tokenUtil.grantToken(user.getId(), user.getRole());
    }

}
