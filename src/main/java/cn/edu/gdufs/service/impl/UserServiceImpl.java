package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.UserMapper;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.service.UserService;
import cn.edu.gdufs.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkPassword(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(MD5Util.encode(password, user.getSalt()))) {
            throw new ApiException("用户名或密码错误");
        }
        return user;
    }
}
