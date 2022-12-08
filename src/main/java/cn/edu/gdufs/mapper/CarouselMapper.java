package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Carousel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@Mapper
public interface CarouselMapper {

    // 查询轮播图列表
    List<Carousel> getCarouselList();

    // 查询轮播图详情
    Carousel getCarouselDetail(long id);

    // 新增轮播图
    void insertCarousel(Carousel carousel);

    // 修改轮播图
    void updateCarousel(Carousel carousel);

    // 删除轮播图
    void deleteCarousel(long id);
}
