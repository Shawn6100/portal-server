package cn.edu.gdufs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureUpdateDTO {

    /**
     * 分享会id
     */
    @NotNull(message = "分享会id不能为空")
    @Min(value = 1, message = "分享会id不能小于1")
    private Long id;

    /**
     * 分享会标题
     */
    @NotBlank(message = "分享会标题不可为空")
    @Length(max = 100, message = "分享会标题长度最大为100字")
    private String title;

    /**
     * 分享会简介
     */
    @NotBlank(message = "分享会简介不可为空")
    private String content;

    /**
     * 分享会封面图路径
     */
    @NotBlank(message = "分享会封面图路径不可为空")
    @Length(max = 200, message = "分享会封面图路径超过最大长度")
    private String path;

    /**
     * 分享会地点
     */
    @NotBlank(message = "分享会地点不可为空")
    @Length(max = 100, message = "分享会地点长度最大为50字")
    private String position;

    /**
     * 分享会日期
     */
    @NotBlank(message = "分享会日期不可为空")
    @Pattern(regexp = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$", message = "日期格式错误")
    private String date;

    /**
     * 分享会开始时间
     */
    @NotBlank(message = "分享会开始时间不可为空")
    @Pattern(regexp = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "开始时间格式错误")
    private String beginTime;

    /**
     * 分享会结束时间
     */
    @Pattern(regexp = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "结束时间格式错误")
    @NotBlank(message = "分享会结束时间不可为空")
    private String endTime;

    /**
     * 分享会可报名数
     */
    @NotNull(message = "分享会报名数不可为空")
    @Min(value = 1, message = "分享会可报名数不能小于1")
    private Integer capacity;
}
