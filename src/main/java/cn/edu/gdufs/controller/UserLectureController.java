package cn.edu.gdufs.controller;

import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * Description: 用户参与分享会控制器
 * Author: 严仕鹏
 * Date: 2022/12/19
 */
@RestController
@RequestMapping("/user/lecture")
@Validated
@RequiredPermission(RoleConstant.ROLE_USER)
public class UserLectureController extends BaseController {

    @Autowired
    private LectureService lectureService;

    /**
     * 分享会报名
     */
    @PostMapping
    public void signUp(@Min(value = 1, message = "分享会id不能小于1") long lectureId) {
        // 报名
        lectureService.signUpLecture(getUserId(), lectureId);
    }

    /**
     * 分享会取消报名
     */
    @DeleteMapping
    public void cancelSignUp(@Min(value = 1, message = "分享会id不能小于1") long lectureId) {
        lectureService.cancelSignUpLecture(getUserId(), lectureId);
    }

}
