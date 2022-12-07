package cn.edu.gdufs.controller;

import cn.edu.gdufs.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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

    @Value("${my-config.is-dev}")
    private boolean isDev;
    @Value("${my-config.remote-address}")
    private String resourcesPrefix;

    @PostMapping("/upload")
    public String uploadFile(HttpServletRequest request, MultipartFile file) {
        return (isDev ? request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() : resourcesPrefix)
                + "/upload/" + fileUtil.upload(file);
    }

}
