package cn.edu.gdufs.controller;

import cn.edu.gdufs.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

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

    @PostMapping("/upload")
    public String uploadFile(HttpServletRequest request, MultipartFile file) throws URISyntaxException {
        URI uri = new URI(request.getRequestURL().toString());
        return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(),
                "/upload", null, null) + "/" + fileUtil.upload(file);
    }

}
