package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

  /**
   * 活动id
   */
  @Min(value = 1, message = "id不能小于1")
  private Long id;

  /**
   * 活动标题
   */
  @NotBlank(message = "活动标题不能为空")
  @Length(max = 100, message = "活动标题长度最大为100字")
  private String title;

  /**
   * 活动正文
   */
  @NotBlank(message = "活动正文不能为空")
  private String content;

  /**
   * 封面图路径
   */
  @NotBlank(message = "封面图路径不能为空")
  @Length(max = 200, message = "封面图路径超过最大长度")
  private String path;

  /**
   * 活动优先级：数字越大优先级越高
   */
  @Min(value = 1, message = "活动优先级不能小于1")
  private Long priority;

  /**
   * 活动时间：精确到日
   */
  private Date time;

  /**
   * 活动的微信公众号链接
   */
  @NotBlank(message = "活动链接路径不能为空")
  @Length(max = 200, message = "活动链接路径超过最大长度")
  private String href;

}
