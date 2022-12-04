package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@Mapper
public interface UserMapper {

    // 根据用户名获取用户信息
    User getUserByUsername(String username);

}
