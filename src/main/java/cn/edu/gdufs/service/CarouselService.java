package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.Carousel;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface CarouselService {

    // 查询轮播图列表
    List<Carousel> getCarouselList();

    // 查询轮播图详情
    Carousel getCarouselDetail(long id);

    // 新增轮播图
    void insertCarousel(Carousel carousel, long userId);

    // 修改轮播图
    void updateCarousel(Carousel carousel, long userId);

    // 删除轮播图
    void deleteCarousel(long id);
}
