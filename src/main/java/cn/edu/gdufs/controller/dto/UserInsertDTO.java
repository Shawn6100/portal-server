package cn.edu.gdufs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertDTO {

    /**
     * 用户邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Length(max = 100, message = "邮箱超过最大长度")
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "[0-9a-zA-z]{6}", message = "验证码格式错误")
    private String verificationCode;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
