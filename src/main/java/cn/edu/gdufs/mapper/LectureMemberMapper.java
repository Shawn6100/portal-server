package cn.edu.gdufs.mapper;

import cn.edu.gdufs.pojo.LectureMember;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/20
 */
@Mapper
public interface LectureMemberMapper {

    // 查询是否取消过报名
    LectureMember checkIsCanceled(long userId, long lectureId);

    // 根据userId和lectureId查询LectureMember
    LectureMember getLectureMemberByUserIdAndLectureId(long userId, long lectureId);

    // 新增数据
    void insertLectureMember(LectureMember lectureMember);

    // 取消报名
    void cancelSignup(long userId, long lectureId);

}
