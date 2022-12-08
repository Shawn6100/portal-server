package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.controller.vo.AdminDetailVO;
import cn.edu.gdufs.controller.vo.BlogForAdminVO;
import cn.edu.gdufs.mapper.BlogMapper;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.pojo.Blog;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.service.BlogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/8
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private AdminService adminService;

    @Override
    public List<Blog> getBlogList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return blogMapper.getBlogList();
    }

    @Override
    public List<BlogForAdminVO> getBlogVOList(int pageNumber, int pageSize) {
        // 分页查询
        List<Blog> blogList = getBlogList(pageNumber, pageSize);

        // 根据管理员id数组获取管理员信息列表
        Set<Long> adminIds = new HashSet<>();
        for (Blog blog : blogList) {
            adminIds.add(blog.getCreateUserId());
            adminIds.add(blog.getUpdateUserId());
        }
        Map<Long, Admin> adminMap = adminService.getAdminMapByIds(adminIds);

        // 数据模型转换
        List<BlogForAdminVO> voList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogForAdminVO blogForAdminVO = new BlogForAdminVO();
            BeanUtils.copyProperties(blog, blogForAdminVO);

            // 添加创建用户信息
            AdminDetailVO createUser = new AdminDetailVO();
            BeanUtils.copyProperties(adminMap.get(blog.getCreateUserId()), createUser);
            blogForAdminVO.setCreateUser(createUser);

            // 添加修改用户信息
            AdminDetailVO updateUser = new AdminDetailVO();
            BeanUtils.copyProperties(adminMap.get(blog.getUpdateUserId()), updateUser);
            blogForAdminVO.setUpdateUser(updateUser);

            voList.add(blogForAdminVO);
        }

        return voList;
    }

    // 根据id查询文章信息
    @Override
    public Blog getBlogById(long id) {
        return blogMapper.getBlogById(id);
    }

    // 新增文章
    @Override
    public void insertBlog(Blog blog, long userId) {
        // 设置创建用户id
        blog.setCreateUserId(userId);
        blog.setUpdateUserId(userId);

        // 新增文章
        blogMapper.insertBlog(blog);
    }

    @Override
    public void updateBlog(Blog blog, long userId) {
        // 设置修改用户id
        blog.setUpdateUserId(userId);
        // 修改文章
        blogMapper.updateBlog(blog);
    }
}
