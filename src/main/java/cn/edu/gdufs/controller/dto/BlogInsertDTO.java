package cn.edu.gdufs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogInsertDTO {

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    @Length(max = 100, message = "文章标题长度最大为100字")
    private String title;

    /**
     * 文章正文
     */
    @NotBlank(message = "文章正文不能为空")
    private String content;

    /**
     * 文章封面图路径
     */
    @NotBlank(message = "封面图路径不能为空")
    @Length(max = 200, message = "封面图路径超过最大长度")
    private String cover;
}
