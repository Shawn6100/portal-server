package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@Mapper
public interface AdminMapper {

    // 根据用户名获取用户信息
    Admin getAdminByUsername(String username);

    // 根据用户id查询用户信息
    Admin getAdminById(long id);

    // 修改密码
    void updatePassword(long id, String password);

}
