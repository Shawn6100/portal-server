package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.mapper.LectureMapper;
import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureMapper lectureMapper;

    // 新增轮播图信息
    @Override
    public void insertLecture(Lecture lecture) {
        lectureMapper.insertLecture(lecture);
    }
}
