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

    // 根据用户邮箱查询用户信息
    User getUserByEmail(String email);
}
