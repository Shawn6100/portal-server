package cn.edu.gdufs.service;

import cn.edu.gdufs.controller.vo.CarouselForAdminVO;
import cn.edu.gdufs.pojo.Carousel;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface CarouselService {

    // 分页查询轮播图列表
    List<Carousel> getCarouselList(int pageNumber, int pageSize);

    // 将轮播图列表转换为轮播图VO列表
    List<CarouselForAdminVO> getCarouselVOList(List<Carousel> carousels);

    // 查询轮播图详情
    Carousel getCarouselDetail(long id);

    // 新增轮播图
    void insertCarousel(Carousel carousel, long userId);

    // 修改轮播图
    void updateCarousel(Carousel carousel, long userId);

    // 删除轮播图
    void deleteCarousel(long id);
}
