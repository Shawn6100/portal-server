package cn.edu.gdufs.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogForAdminVO {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章正文
     */
    private String content;

    /**
     * 文章封面图路径
     */
    private String cover;

    /**
     * 文章创建用户
     */
    private AdminDetailVO createUser;

    /**
     * 文章最后修改用户
     */
    private AdminDetailVO updateUser;
}
