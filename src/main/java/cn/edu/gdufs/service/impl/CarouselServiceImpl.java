package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.controller.vo.AdminDetailVO;
import cn.edu.gdufs.controller.vo.CarouselForAdminVO;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.mapper.CarouselMapper;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.pojo.Carousel;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.service.CarouselService;
import cn.edu.gdufs.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/7
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RedisUtil redisUtil;

    // 分页查询轮播图列表
    @Override
    public List<Carousel> getCarouselList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return carouselMapper.getCarouselList();
    }

    // 将轮播图列表转换为轮播图VO列表
    @Override
    public List<CarouselForAdminVO> getCarouselVOList(List<Carousel> carousels) {

        // 一次性查询管理员用户信息
        Set<Long> adminIds = new HashSet<>();
        for (Carousel carousel : carousels) {
            adminIds.add(carousel.getCreateUserId());
            adminIds.add(carousel.getUpdateUserId());
        }
        Map<Long, Admin> adminMap = adminService.getAdminMapByIds(adminIds);

        // 数据模型转换
        List<CarouselForAdminVO> voList = new ArrayList<>();
        for (Carousel carousel : carousels) {
            CarouselForAdminVO carouselVO = new CarouselForAdminVO();
            BeanUtils.copyProperties(carousel, carouselVO);

            // 添加创建用户信息
            AdminDetailVO createUser = new AdminDetailVO();
            BeanUtils.copyProperties(adminMap.get(carousel.getCreateUserId()), createUser);
            carouselVO.setCreateUser(createUser);

            // 添加修改用户信息
            AdminDetailVO updateUser = new AdminDetailVO();
            BeanUtils.copyProperties(adminMap.get(carousel.getUpdateUserId()), updateUser);
            carouselVO.setUpdateUser(updateUser);

            voList.add(carouselVO);
        }

        return voList;
    }

    // 查询轮播图详情
    @Override
    public Carousel getCarouselDetail(long id) {

        // 从 Redis 中查询
        String key = String.format(CacheConstant.CAROUSEL_INFO, id);
        String carouselString = (String) redisUtil.get(key);
        if (carouselString != null) {
            // 查询到直接返回
            return JSONObject.parseObject(carouselString, Carousel.class);
        }

        // 从 MySQL 中查询
        Carousel carousel = carouselMapper.getCarouselDetail(id);
        if (carousel == null) {
            throw new ApiException("id参数错误，轮播图不存在");
        }

        // 将轮播图信息存入 Redis 中
        redisUtil.set(key, JSON.toJSONString(carousel), CacheConstant.EXPIRE_TIME);

        return carousel;
    }

    // 新增轮播图
    @Override
    public void insertCarousel(Carousel carousel, long userId) {
        carousel.setCreateUserId(userId);
        carousel.setUpdateUserId(userId);

        carouselMapper.insertCarousel(carousel);
    }

    // 修改轮播图
    @Override
    @Transactional  // 开启事务，删除失败回滚
    public void updateCarousel(Carousel carousel, long userId) {
        // 修改轮播图信息
        carousel.setUpdateUserId(userId);
        carouselMapper.updateCarousel(carousel);

        // 删除Redis中的缓存信息
        redisUtil.del(String.format(CacheConstant.CAROUSEL_INFO, carousel.getId()));
    }

    // 删除轮播图
    @Override
    @Transactional  // 开启事务，删除失败回滚
    public void deleteCarousel(long id) {
        // 删除轮播图信息
        carouselMapper.deleteCarousel(id);

        // 删除Redis中的缓存信息
        redisUtil.del(String.format(CacheConstant.CAROUSEL_INFO, id));
    }
}
