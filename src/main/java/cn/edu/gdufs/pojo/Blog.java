package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    /**
     * 文章id
     */
    @Min(value = 1, message = "文章id不能小于1")
    private Long id;

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

    /**
     * 文章创建用户id
     */
    @Min(value = 0, message = "文章创建用户id不能小于1")
    private Long createUserId;

    /**
     * 文章最后修改用户id
     */
    @Min(value = 0, message = "文章最后修改用户id不能小于1")
    private Long updateUserId;

}
