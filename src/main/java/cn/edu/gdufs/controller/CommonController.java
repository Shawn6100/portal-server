package cn.edu.gdufs.controller;

import cn.edu.gdufs.service.CommonService;
import cn.edu.gdufs.util.FileUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@RestController
@RequestMapping("/common")
@Validated
public class CommonController {

    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private CommonService commonService;


    @Value("${my-config.is-dev}")
    private boolean isDev;
    @Value("${my-config.remote-address}")
    private String resourcesPrefix;

    /**
     * 文件上传
     *
     * @param request 请求对象
     * @param file    文件
     * @return 文件访问路径
     */
    @PostMapping("/upload")
    public String uploadFile(HttpServletRequest request, MultipartFile file) {
        return (isDev ? request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() : resourcesPrefix)
                + "/upload/" + fileUtil.upload(file);
    }

    /**
     * 管理员忘记密码发送验证码
     *
     * @param email 邮箱
     */
    @PostMapping("/admin/forget")
    public void adminForgetPasswordSendEmail(@Email(message = "邮箱格式错误") String email) {
        commonService.adminForgetPasswordSendEmail(email);
    }

    /**
     * 管理员忘记密码修改为新密码
     */
    @PutMapping("/admin/forget")
    public void adminForgetPassword(@Email(message = "邮箱格式错误") String email,
                                    @Pattern(regexp = "^[0-9A-Z]{6}$", message = "验证码格式错误") String code,
                                    @Length(min = 6, message = "密码长度不能小于6位") String newPassword) {
        commonService.adminForgetPassword(email, code, newPassword);
    }

    /**
     * 用户注册发送邮箱验证码
     */
    @PostMapping("/user/register/code")
    public void sendRegisterCode(@NotBlank(message = "邮箱不能为空")
                                 @Email(message = "邮箱格式错误")
                                         String email) {
        commonService.userRegisterSendVerificationCode(email);
    }

}
