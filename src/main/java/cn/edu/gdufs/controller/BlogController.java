package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.ApiResponse;
import cn.edu.gdufs.common.PageResult;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.BlogInsertDTO;
import cn.edu.gdufs.controller.dto.BlogUpdateDTO;
import cn.edu.gdufs.controller.vo.AdminDetailVO;
import cn.edu.gdufs.controller.vo.BlogForAdminVO;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.pojo.Blog;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.service.BlogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@RestController
@RequestMapping("/blog")
@Validated
public class BlogController extends BaseController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private AdminService adminService;

    /**
     * 查询所有文章列表
     */
    @GetMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public PageResult<BlogForAdminVO> getBlogList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        // 分页查询文章列表
        List<Blog> blogList = blogService.getBlogList(pageNumber, pageSize);

        // 封装分页结果
        PageResult<BlogForAdminVO> result = new PageResult<>();
        BeanUtils.copyProperties(PageInfo.of(blogList), result);

        // 转换为VO列表，封装到分页结果中
        result.setList(blogService.getBlogVOList(blogList));

        return result;
    }

    /**
     * 查询文章详情
     */
    @GetMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public BlogForAdminVO getBlogDetail(@Min(value = 1, message = "文章id不能小于1") @PathVariable long id) {
        Blog blog = blogService.getBlogById(id);

        // 数据模型转换
        BlogForAdminVO blogForAdminVO = new BlogForAdminVO();
        BeanUtils.copyProperties(blog, blogForAdminVO);

        // 查询创建用户和修改用户信息
        blogForAdminVO.setCreateUser(adminService.getAdminDetail(blog.getCreateUserId()));
        blogForAdminVO.setUpdateUser(adminService.getAdminDetail(blog.getUpdateUserId()));

        return blogForAdminVO;
    }

    /**
     * 新增文章
     */
    @PostMapping
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public BlogForAdminVO insertBlog(@RequestBody @Valid BlogInsertDTO blogInsertDTO) {
        // 数据模型转换
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogInsertDTO, blog);

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

    /**
     * 删除文章
     */
    @DeleteMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public void deleteBlog(@Min(value = 1, message = "文章id不能小于1") @PathVariable long id) {
        blogService.deleteBlog(id);
    }
}
