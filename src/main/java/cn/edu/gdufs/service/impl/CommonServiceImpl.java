package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.service.CommonService;
import cn.edu.gdufs.util.MailUtil;
import cn.edu.gdufs.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/9
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private RedisUtil redisUtil;

    // 管理员忘记密码发送验证码
    @Override
    public void adminForgetPasswordSendEmail(String email) {
        // 检查邮箱是否合法
        if (adminService.getAdminByEmail(email) == null) {
            throw new ApiException("该邮箱不存在");
        }

        // 获取邮箱验证码
        String verificationCode = mailUtil.getVerificationCode();

        // 将验证码存入Redis
        String key = String.format(CacheConstant.ADMIN_FORGET_PASSWORD_CODE, email);
        redisUtil.set(key, verificationCode, 5 * 60 + 10);  // 过期时间五分钟

        // 发送邮件
        mailUtil.sendForgetPasswordVerificationMail(email, verificationCode);
    }
}
