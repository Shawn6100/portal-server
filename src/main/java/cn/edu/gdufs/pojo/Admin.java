package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    /**
     * 管理员id
     */
    @Min(value = 1, message = "管理员id不能小于1")
    private Long id;

    /**
     * 管理员用户名
     */
    @Length(max = 50, message = "用户名长度超出限制")
    private String username;

    /**
     * 管理员密码
     */
    @Length(max = 100, message = "密码长度超出限制")
    private String password;

    /**
     * 管理员盐值
     */
    @Null(message = "非法传参")
    private String salt;

    /**
     * 管理员权限：详见 RoleConstant 类
     */
    @Min(value = 0, message = "管理员权限不能小于0")
    private Integer role;

    /**
     * 管理员昵称
     */
    @NotBlank(message = "管理员昵称不能为空")
    @Length(max = 50, message = "管理员昵称长度超出限制")
    private String nickname;

    /**
     * 管理员邮箱
     */
    @Email(message = "邮箱格式错误")
    private String email;

}
