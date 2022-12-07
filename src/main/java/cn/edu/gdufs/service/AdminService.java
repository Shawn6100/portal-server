package cn.edu.gdufs.service;

import cn.edu.gdufs.controller.vo.AdminDetailVO;
import cn.edu.gdufs.pojo.Admin;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    // 根据用户id数组查询用户信息列表
    Map<Long, Admin> getAdminMapByIds(Collection<Long> ids);

    // 查询管理员用户详情信息
    AdminDetailVO getAdminDetail(long id);

    // 修改密码
    void updatePassword(long id, String password);

    // 查询管理员列表
    List<Admin> getAdminList();

    // 新增管理员
    Admin insertAdmin(Admin admin);

    // 修改管理员信息
    void updateAdmin(Admin admin);

    // 删除管理员
    void deleteAdmin(long id);

}
