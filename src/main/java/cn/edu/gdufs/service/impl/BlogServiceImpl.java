package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.mapper.BlogMapper;
import cn.edu.gdufs.pojo.Blog;
import cn.edu.gdufs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/8
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

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
