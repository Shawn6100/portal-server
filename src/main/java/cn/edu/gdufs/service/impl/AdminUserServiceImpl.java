package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.controller.vo.UserForAdminVO;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.UserMapper;
import cn.edu.gdufs.service.AdminUserService;
import cn.edu.gdufs.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<UserForAdminVO> getUserList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return userMapper.getUserForAdminVOList();
    }

    @Override
    public UserForAdminVO getUserDetail(long id) {
        // 查询Redis中是否存在记录
        String key = String.format(CacheConstant.ADMIN_USER_INFO, id);
        String userDetailString = (String) redisUtil.get(key);
        if (userDetailString != null) {
            // 缓存穿透假值处理
            if ("".equals(userDetailString)) {
                throw new ApiException("用户不存在");
            }
            return JSONObject.parseObject(userDetailString, UserForAdminVO.class);
        }

        // 获取用户详情信息
        UserForAdminVO userDetail = userMapper.getUserDetail(id);

        // MySQL中未查到数据
        if (userDetail == null) {
            // 存入Redis，防止缓存穿透，30秒过期
            redisUtil.set(key, "", CacheConstant.SHORT_EXPIRE_TIME);
            throw new ApiException("用户不存在");
        }

        // 存入Redis
        redisUtil.set(key, JSON.toJSONString(userDetail), CacheConstant.EXPIRE_TIME);

        return userDetail;
    }
}
