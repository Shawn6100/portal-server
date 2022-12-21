package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.Lecture;

import java.util.List;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
public interface LectureService {

    // 分页查询分享会列表
    List<Lecture> getLectureList(int pageNumber, int pageSize);

    // 查询分享会详情
    Lecture getLectureById(long id);

    // 新增分享会信息
    void insertLecture(Lecture lecture);

    // 修改分享会信息
    void updateLecture(Lecture lecture);

    // 删除分享会信息
    void deleteLecture(long id);

    // 用户报名分享会
    void signUpLecture(long userId, long lectureId);

    // 用户取消报名分享会
    void cancelSignUpLecture(long userId, long lectureId);
}
