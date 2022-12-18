package cn.edu.gdufs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description:
 * Author: 冯秋玲
 * Date: 2022/12/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @Min(value = 1, message = "用户id不能小于1")
    private Long id;

    /**
     * 用户头像路径
     */
    @NotBlank(message = "用户头像不能为空")
    @Length(max = 100, message = "用户头像路径过长")
    private String avatar;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 50, message = "用户昵称长度超出限制")
    private String nickname;

}
