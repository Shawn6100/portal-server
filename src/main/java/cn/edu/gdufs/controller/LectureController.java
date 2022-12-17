package cn.edu.gdufs.controller;

import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.LectureInsertDTO;
import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.service.LectureService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
@RestController
@RequestMapping("/lecture")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    /**
     * 新增分享会信息
     */
    @PostMapping
    @RequiredPermission(RoleConstant.ROLE_SUPER_ADMIN)
    public Lecture insertLecture(@RequestBody @Valid LectureInsertDTO lectureInsertDTO) {
        // 数据模型转换
        Lecture lecture = new Lecture();
        BeanUtils.copyProperties(lectureInsertDTO, lecture);

        // 新增分享会
        lectureService.insertLecture(lecture);

        return lecture;
    }
}
