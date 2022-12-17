package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.User;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/18
 */
public interface UserService {

    // 根据用户邮箱查询用户信息
    User getUserByEmail(String email);
}
