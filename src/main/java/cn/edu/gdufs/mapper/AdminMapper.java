package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@Mapper
public interface AdminMapper {

    // 根据用户名获取用户信息
    Admin getAdminByUsername(String username);

    // 根据邮箱获取管理员信息
    Admin getAdminByEmail(String email);

    // 根据用户id查询用户信息
    Admin getAdminById(long id);

    // 根据用户id数组查询用户信息列表
    List<Admin> getAdminListByIds(List<Long> ids);

    // 修改密码
    void updatePassword(long id, String password);

    // 查询管理员列表
    List<Admin> getAdminList();

    // 新增管理员
    void insertAdmin(Admin admin);

    // 修改管理员信息
    void updateAdmin(Admin admin);

    // 删除管理员
    void deleteAdmin(long id);

}
