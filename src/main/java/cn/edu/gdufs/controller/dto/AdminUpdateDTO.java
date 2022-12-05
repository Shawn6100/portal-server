package cn.edu.gdufs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateDTO {

    /**
     * 管理员id
     */
    @NotNull(message = "管理员id不能为空")
    @Min(value = 1, message = "管理员id不能小于1")
    private Long id;

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
