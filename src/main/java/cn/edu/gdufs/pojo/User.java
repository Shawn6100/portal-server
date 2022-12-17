package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户盐值
     */
    private String salt;

    /**
     * 用户头像路径
     */
    private String avatar;

    /**
     * 用户权限
     */
    private Integer role;

    /**
     * 用户昵称
     */
    private String nickname;

}
