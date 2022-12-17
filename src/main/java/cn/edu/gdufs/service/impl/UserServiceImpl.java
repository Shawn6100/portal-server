package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.UserMapper;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.service.UserService;
import cn.edu.gdufs.util.MD5Util;
import cn.edu.gdufs.util.RedisUtil;
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

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void register(User user, String code) {
        // 校验验证码是否正确
        String key = String.format(CacheConstant.USER_REGISTER_CODE, user.getEmail());
        String verificationCode = (String) redisUtil.get(key);
        if (verificationCode == null || !verificationCode.equals(code)) {
            throw new ApiException("验证码错误");
        }

        // 加密密码
        user.setSalt(MD5Util.getSalt());
        user.setPassword(MD5Util.encode(user.getPassword(), user.getSalt()));
        // 设置昵称
        user.setNickname(user.getEmail());

        // 新增用户
        userMapper.insertUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }
}
