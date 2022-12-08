package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
  @NotNull
  @Min(value = 1, message = "活动优先级不能小于1")
  private Long priority;

  /**
   * 活动时间：精确到日；格式：yyyy-MM-dd
   */
  @NotNull
  @Pattern(regexp = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)", message = "日期格式错误")
  private String time;

  /**
   * 活动的微信公众号链接
   */
  @NotBlank(message = "活动链接路径不能为空")
  @Length(max = 200, message = "活动链接路径超过最大长度")
  private String href;

}
