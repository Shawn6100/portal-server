package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: 欧丹萍
 * String: 2022/12/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    /**
     * 分享会id
     */
    private long id;

    /**
     * 分享会标题
     */
    private String title;

    /**
     * 分享会简介
     */
    private String content;

    /**
     * 分享会封面图路径
     */
    private String path;

    /**
     * 分享会日期
     */
    private String date;

    /**
     * 分享会开始时间
     */
    private String beginTime;

    /**
     * 分享会结束时间
     */
    private String endTime;

    /**
     * 分享会可报名数
     */
    private long capacity;

    /**
     * 分享会状态
     */
    private long status;

}
