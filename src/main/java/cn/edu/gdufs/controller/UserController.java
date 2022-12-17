package cn.edu.gdufs.controller;

import cn.edu.gdufs.controller.dto.UserInsertDTO;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
