package cn.edu.gdufs.controller;

import cn.edu.gdufs.service.CommonService;
import cn.edu.gdufs.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

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
     * @param request   请求对象
     * @param file  文件
     * @return 文件访问路径
     */
    @PostMapping("/upload")
    public String uploadFile(HttpServletRequest request, MultipartFile file) {
        return (isDev ? request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() : resourcesPrefix)
                + "/upload/" + fileUtil.upload(file);
    }

    /**
     * 管理员忘记密码发送验证码
     * @param email 邮箱
     */
    @PostMapping("/admin/forget")
    public void adminForgetPasswordSendEmail(@Email(message = "邮箱格式错误") String email) {
        commonService.adminForgetPasswordSendEmail(email);
    }

}
