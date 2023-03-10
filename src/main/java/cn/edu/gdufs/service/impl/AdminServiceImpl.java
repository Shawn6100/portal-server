package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.vo.AdminDetailVO;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.AdminMapper;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.util.MD5Util;
import cn.edu.gdufs.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisUtil redisUtil;

    // 登录
    @Override
    public Admin login(String username, String password) {
        Admin admin = adminMapper.getAdminByUsername(username);
        if (checkPassword(admin, password)) {
            throw new ApiException("用户名或密码错误");
        }
        return admin;
    }

    // 校验用户名和密码是否错误
    @Override
    public boolean checkPassword(Admin admin, String password) {
        return admin == null || !admin.getPassword().equals(MD5Util.encode(password, admin.getSalt()));
    }

    // 根据用户邮箱查询用户信息
    @Override
    public Admin getAdminByEmail(String email) {
        return adminMapper.getAdminByEmail(email);
    }

    // 根据用户id查询用户信息
    @Override
    public Admin getAdminById(long id) {
        return adminMapper.getAdminById(id);
    }

    // 根据用户id数组查询用户信息列表
    @Override
    public AdminDetailVO getAdminDetail(long id) {
        // 先从Redis中查询
        String key = String.format(CacheConstant.ADMIN_INFO, id);
        String adminDetailString = (String) redisUtil.get(key);
        if (!StringUtils.isEmptyOrWhitespaceOnly(adminDetailString)) {
            return JSONObject.parseObject(adminDetailString, AdminDetailVO.class);
        }

        // 查询数据库
        Admin admin = getAdminById(id);
        if (admin == null) {
            throw new ApiException("管理员不存在");
        }

        // 数据模型转换
        AdminDetailVO adminDetailVO = new AdminDetailVO(admin.getId(), admin.getUsername(),
                admin.getRole(), admin.getNickname(), admin.getEmail());

        // 将管理员信息存入Redis中缓存
        redisUtil.set(key, JSON.toJSONString(adminDetailVO), CacheConstant.EXPIRE_TIME);

        return adminDetailVO;
    }

    // 根据用户id数组查询用户信息列表
    @Override
    public Map<Long, Admin> getAdminMapByIds(Collection<Long> ids) {
        Map<Long, Admin> result = new HashMap<>();
        adminMapper.getAdminListByIds(new ArrayList<>(ids)).forEach(admin -> result.put(admin.getId(), admin));
        return result;
    }

    // 修改密码
    @Override
    public void updatePassword(long id, String password) {
        adminMapper.updatePassword(id, password);
    }

    // 查询管理员列表
    @Override
    public List<Admin> getAdminList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return adminMapper.getAdminList();
    }

    // 新增管理员
    @Override
    public Admin insertAdmin(Admin admin) {
        // 检验账号重复
        if (adminMapper.getAdminByUsername(admin.getUsername()) != null) {
            throw new ApiException("用户名已存在");
        }
        String email = admin.getEmail();
        if (!StringUtils.isEmptyOrWhitespaceOnly(email) && adminMapper.getAdminByEmail(email) != null) {
            throw new ApiException("邮箱已存在");
        }

        // 未填写昵称，设置默认昵称为用户名
        String nickname = admin.getNickname();
        if (StringUtils.isEmptyOrWhitespaceOnly(nickname)) {
            admin.setNickname(admin.getUsername());
        }

        // 设置默认权限、盐值，加密密码
        admin.setRole(RoleConstant.ROLE_NORMAL_ADMIN);
        admin.setSalt(MD5Util.getSalt());
        admin.setPassword(MD5Util.encode(admin.getPassword(), admin.getSalt()));

        // 新增管理员用户
        adminMapper.insertAdmin(admin);
        return admin;
    }

    // 修改管理员信息
    @Override
    @Transactional  // 开启事务
    public void updateAdmin(Admin admin) {
        // 判空
        if (StringUtils.isEmptyOrWhitespaceOnly(admin.getNickname())) {
            admin.setNickname(null);
        }
        if (StringUtils.isEmptyOrWhitespaceOnly(admin.getEmail())) {
            admin.setEmail(null);
        }

        // 修改管理员信息
        adminMapper.updateAdmin(admin);

        // 删除Redis中的缓存信息（如果有）
        String key = String.format(CacheConstant.ADMIN_INFO, admin.getId());
        if (redisUtil.get(key) != null) {
            redisUtil.del(key);
        }
    }

    // 删除管理员
    @Override
    @Transactional  // 开启事务
    public void deleteAdmin(long id) {
        adminMapper.deleteAdmin(id);

        // 删除Redis中的缓存信息（如果有）
        String key = String.format(CacheConstant.ADMIN_INFO, id);
        if (redisUtil.get(key) != null) {
            redisUtil.del(key);
        }
    }
}
