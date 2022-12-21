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
public class LectureFrontVO {

    /**
     * 分享会id
     */
    @JSONField(ordinal = 1)
    private Long id;

    /**
     * 分享会标题
     */
    @JSONField(ordinal = 2)
    private String title;

    /**
     * 分享会简介
     */
    @JSONField(ordinal = 3)
    private String content;

    /**
     * 分享会封面图路径
     */
    @JSONField(ordinal = 4)
    private String path;

    /**
     * 分享会地点
     */
    @JSONField(ordinal = 5)
    private String position;

    /**
     * 分享会日期
     */
    @JSONField(ordinal = 6)
    private String date;

    /**
     * 分享会开始时间
     */
    @JSONField(ordinal = 7)
    private String beginTime;

    /**
     * 分享会结束时间
     */
    @JSONField(ordinal = 8)
    private String endTime;

    /**
     * 分享会可报名数
     */
    @JSONField(ordinal = 9)
    private Integer capacity;

    /**
     * 分享会状态
     */
    @JSONField(ordinal = 10)
    private Integer status;

}
