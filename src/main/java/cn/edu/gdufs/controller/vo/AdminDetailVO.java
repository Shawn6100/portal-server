package cn.edu.gdufs.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDetailVO {

    /**
     * 管理员id
     */
    private Long id;

    /**
     * 管理员用户名
     */
    private String username;

    /**
     * 管理员权限
     */
    private Integer role;

    /**
     * 管理员昵称
     */
    private String nickname;

    /**
     * 管理员邮箱
     */
    private String email;
}
