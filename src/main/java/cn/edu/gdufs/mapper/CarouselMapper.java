package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Carousel;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@Mapper
public interface CarouselMapper {

    // 新增轮播图
    void insertCarousel(Carousel carousel);
}
