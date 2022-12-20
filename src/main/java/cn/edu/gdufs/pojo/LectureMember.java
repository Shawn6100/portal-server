package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureMember {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分享会id
     */
    private Long lectureId;

    public LectureMember(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }

}
