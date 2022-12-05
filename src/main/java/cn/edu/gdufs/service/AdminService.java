package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.Admin;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface AdminService {

    // 登录
    Admin login(String username, String password);

    // 校验用户名和密码是否错误
    boolean checkPassword(Admin admin, String password);

    // 根据用户id查询用户信息
    Admin getAdminById(long id);

    // 修改密码
    void updatePassword(long id, String password);

    // 查询管理员列表
    List<Admin> getAdminList();

}
