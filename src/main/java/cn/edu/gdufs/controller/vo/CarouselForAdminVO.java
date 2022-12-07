package cn.edu.gdufs.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarouselForAdminVO {

    /**
     * 轮播图id
     */
    private Long id;

    /**
     * 轮播图简介
     */
    private String content;

    /**
     * 轮播图路径
     */
    private String path;

    /**
     * 轮播图优先级：数字越大优先级越高
     */
    private Long priority;

    /**
     * 轮播图创建用户
     */
    private AdminDetailVO createUser;

    /**
     * 轮播图最后修改用户
     */
    private AdminDetailVO updateUser;
}
