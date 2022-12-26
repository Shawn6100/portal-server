package cn.edu.gdufs.scheduler;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.mapper.UserMapper;
import cn.edu.gdufs.pojo.Lecture;
import cn.edu.gdufs.pojo.User;
import cn.edu.gdufs.thread.NoticeThread;
import cn.edu.gdufs.util.MailUtil;
import cn.edu.gdufs.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: 定时任务器
 * Author: 欧丹萍
 * Date: 2022/12/25
 */
@Component
@EnableScheduling
public class NoticeListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private NoticeUserAsync noticeUserAsync;

    // 定时任务：每日上午九时检测是否需要发送活动上线通知
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkNotice() {

        logger.info("活动上线通知检测定时任务正在执行...");

        // 从消息队列中取出消息
        Lecture lecture = (Lecture) redisUtil.lPop(CacheConstant.NEW_LECTURE_NOTICE);
        while (lecture != null) {

            logger.info("有新消息，发送给所有用户");
            // 有新消息，发送给所有用户
            noticeUserAsync.noticeUserNewLecture(lecture);
            logger.info("异步吗？");

            lecture = (Lecture) redisUtil.lPop(CacheConstant.NEW_LECTURE_NOTICE);
        }
    }
}

@Component
class NoticeUserAsync {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Async("taskExecutor")
    public void noticeUserNewLecture(Lecture lecture) {
        // 获取用户列表
        List<User> userList = userMapper.getUserList();

        userList.forEach(user -> threadPoolTaskExecutor.execute(new NoticeThread(mailUtil, user, lecture)));
    }
}
