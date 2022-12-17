package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.mapper.UserMapper;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }
}
