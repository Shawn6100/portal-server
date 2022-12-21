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
public class ActivityFrontVO {
    /**
     * 活动id
     */
    @JSONField(ordinal = 1)
    private Long id;

    /**
     * 活动标题
     */
    @JSONField(ordinal = 2)
    private String title;

    /**
     * 活动正文
     */
    @JSONField(ordinal = 3)
    private String content;

    /**
     * 封面图路径
     */
    @JSONField(ordinal = 4)
    private String path;

    /**
     * 活动优先级：数字越大优先级越高
     */
    @JSONField(ordinal = 5)
    private Long priority;

    /**
     * 活动时间：精确到日；格式：yyyy-MM-dd
     */
    @JSONField(ordinal = 6)
    private String time;

    /**
     * 活动的微信公众号链接
     */
    @JSONField(ordinal = 7)
    private String href;

}
