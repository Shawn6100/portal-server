package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Lecture;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
@Mapper
public interface LectureMapper {

    // 查询分享会列表
    List<Lecture> getLectureList();

    // 查询分享会详情
    Lecture getLectureById(long id);

    // 新增轮播图信息
    void insertLecture(Lecture lecture);

    // 修改分享会信息
    void updateLecture(Lecture lecture);

    // 删除分享会信息
    void deleteLecture(long id);
}
