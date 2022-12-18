package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/18
 */
@Mapper
public interface UserMapper {

    // 新增用户
    void insertUser(User user);

    // 根据用户邮箱查询用户信息
    User getUserByEmail(String email);

    // 根据id获取用户信息
    User getUserById(long id);

    // 更新用户信息
    void updateUser(User user);

    // 修改用户密码
    void updatePassword(long id, String password);
}
