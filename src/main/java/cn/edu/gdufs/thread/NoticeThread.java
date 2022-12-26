package cn.edu.gdufs.thread;

import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.util.MailUtil;

/**
 * Description:
 * Author: 欧丹萍
 * Date: 2022/12/25
 */
public class NoticeThread extends Thread {

    private final MailUtil mailUtil;
    private final User user;
    private final Lecture lecture;

    public NoticeThread(MailUtil mailUtil, User user, Lecture lecture) {
        this.mailUtil = mailUtil;
        this.user = user;
        this.lecture = lecture;
    }

    @Override
    public void run() {
        // 发送邮件
        mailUtil.sendNewLectureNotice(user, lecture);
    }
}
