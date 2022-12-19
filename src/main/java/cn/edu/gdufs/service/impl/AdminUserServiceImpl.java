package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.controller.vo.UserForAdminVO;
import cn.edu.gdufs.mapper.UserMapper;
import cn.edu.gdufs.service.AdminUserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/19
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserForAdminVO> getUserList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return userMapper.getUserForAdminVOList();
    }

    @Override
    public UserForAdminVO getUserDetail(long id) {
        return userMapper.getUserDetail(id);
    }
}
