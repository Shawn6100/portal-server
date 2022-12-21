package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.PageResult;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.service.LectureService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

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

    /**
     * 查看我参与的分享会列表
     */
    @GetMapping
    public PageResult<Lecture> getMyLectureList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                @RequestParam(defaultValue = "5") Integer pageSize) {
        // 查询数据
        List<Lecture> lectureList = lectureService.getUserAttendLectureList(getUserId(), pageNumber, pageSize);

        // 封装分页数据
        PageResult<Lecture> result = new PageResult<>();
        BeanUtils.copyProperties(PageInfo.of(lectureList), result);

        return result;
    }

}
