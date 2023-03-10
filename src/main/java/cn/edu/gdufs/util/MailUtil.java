package cn.edu.gdufs.util;

import cn.edu.gdufs.exception.ApiException;
import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * Description: 邮件工具类
 * Author: 严仕鹏
 * Date: 2022/12/9
 */
@Component
public class MailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    // 发件邮箱
    @Value("${spring.mail.username}")
    private String mailFrom;

    /**
     * 发送邮件方法
     *
     * @param recipients 收件人邮箱数组
     * @param title      邮件标题
     * @param content    邮件正文
     */
    private void sendMail(String[] recipients, String title, String content) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "utf-8");
            messageHelper.setFrom(mailFrom, "广外Qt官网展示系统");
            messageHelper.setTo(recipients);
            messageHelper.setSubject(title);
            messageHelper.setText(content, false);
            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new ApiException("邮件发送出错！");
        }
    }

    /**
     * 忘记密码发送验证码
     *
     * @param recipient        接收邮箱
     * @param verificationCode 验证码
     */
    @Async("taskExecutor")  // 异步发送邮件
    public void sendForgetPasswordVerificationMail(String recipient, String verificationCode) {
        String title = "【广外Qt官网展示系统】忘记密码验证码";
        String template = "您正在修改【广外Qt官网展示系统】登录密码，您的验证码为 %s ，5分钟内有效。";
        String mailBody = String.format(template, verificationCode);
        sendMail(new String[]{recipient}, title, mailBody);
    }

    /**
     * 用户注册发送验证码
     */
    @Async("taskExecutor")
    public void sendUserRegisterVerificationCode(String recipient, String verificationCode) {
        String title = "【广外Qt官网展示系统】注册验证码";
        String template = "您正在注册【广外Qt官网展示系统】，您的验证码为 %s ，5分钟内有效。";
        String mailBody = String.format(template, verificationCode);
        sendMail(new String[]{recipient}, title, mailBody);
    }

    /**
     * 发送新分享会上线通知
     */
    @Async("taskExecutor")
    public void sendNewLectureNotice(User user, Lecture lecture) {
        String title = "【广外Qt官网展示系统】分享会上线通知";
        String mailBody = user.getNickname() + "，您好！\n"
                + "“" + lecture.getTitle() + "”现已开放报名，本期分享会举办日期为 " + lecture.getDate() + "，"
                + "欢迎登录官网展示系统报名此活动！";
        sendMail(new String[]{user.getEmail()}, title, mailBody);
    }

    /**
     * 生成邮箱验证码，码长6位，大写字母与数字混合
     *
     * @return 6位验证码
     */
    public String getVerificationCode() {
        // 增大数字权重，去除部分相似字母
        final String CHARACTERS = "01234567890123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            sb.append(CHARACTERS.charAt((int) (Math.random() * (CHARACTERS.length()))));
        }
        return sb.toString();
    }
}
