package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.User;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/18
 */
public interface UserService {

    // 用户注册
    void register(User user, String verificationCode);

    // 用户登录
    User login(String email, String password);

    // 校验密码是否正确
    boolean checkPassword(User user, String password);

    // 根据用户邮箱查询用户信息
    User getUserByEmail(String email);

    // 根据id获取用户信息
    User getUserById(long id);

}
