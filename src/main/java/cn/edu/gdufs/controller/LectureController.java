package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.PageResult;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.LectureInsertDTO;
import cn.edu.gdufs.controller.dto.LectureUpdateDTO;
import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.service.LectureService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
@RestController
@RequestMapping("/lecture")
@Validated
public class LectureController {

    @Autowired
    private LectureService lectureService;

    /**
     * 查询分享会列表
     */
    @GetMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public PageResult<Lecture> getLectureList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                              @RequestParam(defaultValue = "5") Integer pageSize) {
        // 分页查询分享会列表
        List<Lecture> lectureList = lectureService.getLectureList(pageNumber, pageSize);

        // 封装分页数据
        PageResult<Lecture> result = new PageResult<>();
        BeanUtils.copyProperties(PageInfo.of(lectureList), result);

        return result;
    }

    /**
     * 查询分享会详情
     */
    @GetMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public Lecture getLectureDetail(@Min(value = 1, message = "分享会id不能小于1") @PathVariable long id) {
        // 查询分享会详情
        return lectureService.getLectureById(id);
    }

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

    /**
     * 修改分享会信息
     */
    @PutMapping
    @RequiredPermission(RoleConstant.ROLE_SUPER_ADMIN)
    public void updateLecture(@RequestBody @Valid LectureUpdateDTO lectureUpdateDTO) {
        // 数据模型转换
        Lecture lecture = new Lecture();
        BeanUtils.copyProperties(lectureUpdateDTO, lecture);

        // 修改分享会信息
        lectureService.updateLecture(lecture);
    }

    /**
     * 删除分享会信息
     */
    @DeleteMapping("/{id}")
    @RequiredPermission(RoleConstant.ROLE_SUPER_ADMIN)
    public void deleteLecture(@Min(value = 1, message = "分享会id不能小于1") @PathVariable long id) {
        // 删除分享会信息
        lectureService.deleteLecture(id);
    }
}
