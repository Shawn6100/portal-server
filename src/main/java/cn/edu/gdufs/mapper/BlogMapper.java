package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@Mapper
public interface BlogMapper {

    // 新增文章
    void insertBlog(Blog blog);
}
