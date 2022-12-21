package cn.edu.gdufs.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogFrontVO {

    /**
     * 文章id
     */
    @JSONField(ordinal = 1)
    private Long id;

    /**
     * 文章标题
     */
    @JSONField(ordinal = 2)
    private String title;

    /**
     * 文章正文
     */
    @JSONField(ordinal = 3)
    private String content;

    /**
     * 文章封面图路径
     */
    @JSONField(ordinal = 4)
    private String cover;

    /**
     * 文章作者
     */
    @JSONField(ordinal = 5)
    private String author;

}
