package cn.edu.gdufs.util;

import cn.edu.gdufs.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/5
 */
@Component
public class FileUtil {

    // 允许上传的文件类型
    private final Set<String> ALLOW_FILE_TYPE;

    private FileUtil() {
        ALLOW_FILE_TYPE = new HashSet<>();
        ALLOW_FILE_TYPE.add("jpg");
        ALLOW_FILE_TYPE.add("png");
        ALLOW_FILE_TYPE.add("gif");
        ALLOW_FILE_TYPE.add("webp");
    }

    // 文件上传路径
    @Value("${my-config.file-upload-path}")
    private String filePath;

    /**
     * 文件上传方法
     * @param file 文件
     * @return 路径
     */
    public String upload(MultipartFile file) {
        String filename;
        String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        try {
            // 获取源文件名
            filename = file.getOriginalFilename();
            if (filename == null) {
                throw new ApiException("文件名不能为空");
            }
            // 获取文件后缀名
            String suffix = filename.substring(filename.lastIndexOf("."));
            // 获取文件类型
            if (!ALLOW_FILE_TYPE.contains(suffix.substring(1).trim().toLowerCase())) {
                throw new ApiException("文件类型不允许");
            }
            // 目标文件夹路径
            String directoryPath = filePath + format;
            File directory = new File(directoryPath);
            if (!directory.exists() && !directory.mkdir()) {
                throw new ApiException("文件夹创建失败，请重试");
            }
            // 重命名文件
            filename = DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis()).getBytes()) + suffix;
            // 目标文件
            File destFile = new File(directoryPath + "/" + filename);
            // 保存文件
            file.transferTo(destFile);
            if (destFile.length() > 10 * 1024 * 1024) {
                throw new ApiException("上传文件不能大于10M");
            }
        } catch (IOException e) {
            throw new ApiException("文件上传失败，请重试");
        }
        return format + "/" + filename;
    }
}
