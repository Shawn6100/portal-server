package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.Admin;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface AdminService {

    // 校验用户名和密码是否正确
    Admin checkPassword(String username, String password);

}
