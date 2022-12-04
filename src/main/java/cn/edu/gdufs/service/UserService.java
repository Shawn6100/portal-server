package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.User;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface UserService {

    // 校验用户名和密码是否正确
    User checkPassword(String username, String password);

}
