package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Lecture;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
@Mapper
public interface LectureMapper {

    // 新增轮播图信息
    void insertLecture(Lecture lecture);
}
