package cn.edu.gdufs.controller;

import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.pojo.Blog;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@RestController
@RequestMapping("/blog")
public class BlogController extends BaseController {

    /**
     * 新增文章
     */
    @PostMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public void insertBlog(@RequestBody @Valid Blog blog) {

    }
}
