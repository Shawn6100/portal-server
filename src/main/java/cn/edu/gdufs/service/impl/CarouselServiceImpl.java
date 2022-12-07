package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.mapper.CarouselMapper;
import cn.edu.gdufs.pojo.Carousel;
import cn.edu.gdufs.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/7
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public void insertCarousel(Carousel carousel, long userId) {
        carousel.setCreateUser(userId);
        carousel.setUpdateUser(userId);

        carouselMapper.insertCarousel(carousel);
    }

    @Override
    public void updateCarousel(Carousel carousel, long userId) {
        carousel.setUpdateUser(userId);
        carouselMapper.updateCarousel(carousel);
    }

    @Override
    public void deleteCarousel(long id) {
        carouselMapper.deleteCarousel(id);
    }
}
