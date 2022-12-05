package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.AdminMapper;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Admin login(String username, String password) {
        Admin admin = adminMapper.getAdminByUsername(username);
        if (checkPassword(admin, password)) {
            throw new ApiException("用户名或密码错误");
        }
        return admin;
    }

    @Override
    public boolean checkPassword(Admin admin, String password) {
        return admin == null || !admin.getPassword().equals(MD5Util.encode(password, admin.getSalt()));
    }

    @Override
    public Admin getAdminById(long id) {
        return adminMapper.getAdminById(id);
    }

    @Override
    public void updatePassword(long id, String password) {
        adminMapper.updatePassword(id, password);
    }

    @Override
    public List<Admin> getAdminList() {
        return adminMapper.getAdminList();
    }
}
