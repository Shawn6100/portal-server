package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@Mapper
public interface BlogMapper {

    // 查询所有文章列表
    List<Blog> getBlogList();

    // 根据id查询文章信息
    Blog getBlogById(long id);

    // 新增文章
    void insertBlog(Blog blog);

    // 修改文章
    void updateBlog(Blog blog);
}
