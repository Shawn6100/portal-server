package cn.edu.gdufs.service;

import cn.edu.gdufs.controller.vo.BlogForAdminVO;
import cn.edu.gdufs.pojo.Blog;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
public interface BlogService {

    // 查询所有文章列表
    List<Blog> getBlogList();

    // 分页查询所有文章列表
    List<Blog> getBlogListByPage(int pageNumber, int pageSize);

    // 将文章列表转换为文章VO列表
    List<BlogForAdminVO> getBlogVOList(List<Blog> blogList);

    // 根据id查询文章信息
    Blog getBlogById(long id);

    // 新增文章
    void insertBlog(Blog blog, long userId);

    // 修改文章
    void updateBlog(Blog blog, long userId);

    // 删除文章
    void deleteBlog(long id);
}
