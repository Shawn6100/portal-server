package cn.edu.gdufs.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 统一分页结果封装
 * Author: 严仕鹏
 * Date: 2022/12/8
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 页码
     */
    @JSONField(ordinal = 1)
    private int pageNum;

    /**
     * 页面大小
     */
    @JSONField(ordinal = 2)
    private int pageSize;

    /**
     * 该页记录数
     */
    @JSONField(ordinal = 3)
    private int size;

    /**
     * 总页数
     */
    @JSONField(ordinal = 4)
    private int pages;

    /**
     * 数据列表
     */
    @JSONField(ordinal = 5)
    private List<T> list;

}
