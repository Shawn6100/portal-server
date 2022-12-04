package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.AdminMapper;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin checkPassword(String username, String password) {
        Admin admin = adminMapper.getUserByUsername(username);
        if (admin == null || !admin.getPassword().equals(MD5Util.encode(password, admin.getSalt()))) {
            throw new ApiException("用户名或密码错误");
        }
        return admin;
    }
}
