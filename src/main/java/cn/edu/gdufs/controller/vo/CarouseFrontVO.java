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
public class CarouseFrontVO {

    /**
     * 轮播图id
     */
    @JSONField(ordinal = 1)
    private Long id;

    /**
     * 轮播图简介
     */
    @JSONField(ordinal = 2)
    private String content;

    /**
     * 轮播图路径
     */
    @JSONField(ordinal = 3)
    private String path;

    /**
     * 轮播图优先级：数字越大优先级越高
     */
    @JSONField(ordinal = 4)
    private Long priority;
}
