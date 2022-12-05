package cn.edu.gdufs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminInsertDTO {

    /**
     * 管理员用户名
     */
    @NotBlank(message = "管理员用户名不能为空")
    @Length(max = 50, message = "用户名长度超出限制")
    private String username;

    /**
     * 管理员密码
     */
    @NotBlank(message = "管理员密码不能为空")
    @Length(max = 100, message = "密码长度超出限制")
    private String password;

    /**
     * 管理员昵称
     */
    @Length(max = 50, message = "管理员昵称长度超出限制")
    private String nickname;

    /**
     * 管理员邮箱
     */
    @Email(message = "邮箱格式错误")
    private String email;
}
