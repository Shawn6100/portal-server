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
 * Author: 欧丹萍
 * Date: 2022/12/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarouselInsertDTO {

    /**
     * 轮播图简介
     */
    @NotBlank(message = "轮播图简介不能为空")
    @Length(max = 100, message = "轮播图简介长度最大为100字")
    private String content;

    /**
     * 轮播图路径
     */
    @NotBlank(message = "轮播图路径不能为空")
    @Length(max = 200, message = "轮播图路径超过最大长度")
    private String path;

    /**
     * 轮播图优先级：数字越大优先级越高
     */
    @NotNull
    @Min(value = 1, message = "轮播图优先级不能小于1")
    private Long priority;
}
