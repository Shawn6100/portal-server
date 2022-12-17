package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.mapper.LectureMapper;
import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.service.LectureService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureMapper lectureMapper;

    @Override
    public List<Lecture> getLectureList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return lectureMapper.getLectureList();
    }

    @Override
    public Lecture getLectureById(long id) {
        return lectureMapper.getLectureById(id);
    }

    // 新增轮播图信息
    @Override
    public void insertLecture(Lecture lecture) {
        lectureMapper.insertLecture(lecture);
    }

    @Override
    public void updateLecture(Lecture lecture) {
        lectureMapper.updateLecture(lecture);
    }

    @Override
    public void deleteLecture(long id) {
        lectureMapper.deleteLecture(id);
    }
}
