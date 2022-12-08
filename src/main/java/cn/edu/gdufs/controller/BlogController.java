package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.ApiResponse;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.BlogUpdateDTO;
import cn.edu.gdufs.controller.vo.AdminDetailVO;
import cn.edu.gdufs.controller.vo.BlogForAdminVO;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.pojo.Blog;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.service.BlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BlogService blogService;
    @Autowired
    private AdminService adminService;

    /**
     * 新增文章
     */
    @PostMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public BlogForAdminVO insertBlog(@RequestBody @Valid Blog blog) {
        // 新增文章
        blogService.insertBlog(blog, getUserId());

        // 数据模型转换
        BlogForAdminVO blogForAdminVO = new BlogForAdminVO();
        BeanUtils.copyProperties(blog, blogForAdminVO);

        // 查询创建用户信息，并添加到 VO 中
        Admin admin = adminService.getAdminById(blog.getCreateUserId());
        blogForAdminVO.setCreateUser(new AdminDetailVO(admin.getId(), admin.getUsername(),
                admin.getRole(), admin.getNickname(), admin.getEmail()));

        return blogForAdminVO;
    }

    /**
     * 修改文章
     */
    @PutMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public ApiResponse<Object> updateBlog(@RequestBody @Valid BlogUpdateDTO blogUpdateDTO) {
        // 查询文章是否存在
        Blog tempBlog = blogService.getBlogById(blogUpdateDTO.getId());
        if (tempBlog == null) {
            throw new ApiException("文章id参数错误，文章不存在");
        }
        // 权限拦截，普通管理员只能修改自己创建的文章
        if (getUserRole() != RoleConstant.ROLE_SUPER_ADMIN && getUserId() != tempBlog.getCreateUserId()) {
            return ApiResponse.permissionError();
        }

        // 数据模型转换
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogUpdateDTO, blog);

        // 修改文章
        blogService.updateBlog(blog, getUserId());
        return ApiResponse.success();
    }
}
