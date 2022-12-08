package cn.edu.gdufs.service;

import cn.edu.gdufs.pojo.Blog;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface BlogService {

    // 新增文章
    void insertBlog(Blog blog, long userId);

    // 修改文章
    void updateBlog(Blog blog, long userId);
}
