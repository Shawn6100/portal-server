package cn.edu.gdufs.service.impl;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.service.CommonService;
import cn.edu.gdufs.util.MD5Util;
import cn.edu.gdufs.util.MailUtil;
import cn.edu.gdufs.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 检测是否频繁操作
        String lock = String.format(CacheConstant.EMAIL_LOCK, email);
        if (redisUtil.hasKey(lock)) {
            throw new ApiException("操作频繁，请稍后重试");
        }

        // 获取邮箱验证码
        String verificationCode = mailUtil.getVerificationCode();

        // 将验证码存入Redis
        String key = String.format(CacheConstant.ADMIN_FORGET_PASSWORD_CODE, email);
        redisUtil.set(key, verificationCode, 5 * 60 + 10);  // 过期时间五分钟

        // 邮件发送锁
        redisUtil.set(String.format(lock, email), 1, 30);

        // 发送邮件
        mailUtil.sendForgetPasswordVerificationMail(email, verificationCode);
    }

    // 管理员忘记密码修改为新密码
    @Override
    @Transactional
    public void adminForgetPassword(String email, String code, String newPassword) {

        // 检查验证码是否正确
        String key = String.format(CacheConstant.ADMIN_FORGET_PASSWORD_CODE, email);
        String verificationCode = (String) redisUtil.get(key);
        if (verificationCode == null || !verificationCode.equals(code)) {
            throw new ApiException("验证码错误");
        }

        // 修改为新密码
        Admin admin = adminService.getAdminByEmail(email);
        adminService.updatePassword(admin.getId(), MD5Util.encode(newPassword, admin.getSalt()));

        // 删除该用户的 token 和 Redis 中的验证码
        String tokenKey = String.format(CacheConstant.TOKEN_KEY, admin.getId());
        String token = (String) redisUtil.get(tokenKey);
        redisUtil.del(key, tokenKey, token);
    }
}
