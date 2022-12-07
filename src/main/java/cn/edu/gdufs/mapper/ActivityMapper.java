package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@Mapper
public interface ActivityMapper {

    // 查询活动列表
    List<Activity> getActivityList();
}
