package cn.edu.gdufs.service;

import cn.edu.gdufs.controller.vo.UserForAdminVO;

import java.util.List;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/19
 */
public interface AdminUserService {

    // 分页查询用户列表
    List<UserForAdminVO> getUserList(int pageNumber, int pageSize);
}
