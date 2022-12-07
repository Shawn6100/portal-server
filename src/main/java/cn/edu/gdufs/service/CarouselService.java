package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.Carousel;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface CarouselService {

    // 新增轮播图
    void insertCarousel(Carousel carousel, long userId);

    // 修改轮播图
    void updateCarousel(Carousel carousel, long userId);
}
