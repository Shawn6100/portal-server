package cn.edu.gdufs.constant;

/**
 * Description: 缓存中key的模板
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
public class CacheConstant {
    private CacheConstant() {}

    public static final long EXPIRE_TIME = 60 * 60 * 3;         // 通用缓存时间：三小时

    public static final String TOKEN_KEY = "user_token_%s";     // 用户token
    public static final String TOKEN_INFO = "%s_%s";            // token存储的信息
    public static final String ADMIN_INFO = "admin_info_%s";    // 管理员用户详情信息

    public static final String ADMIN_FORGET_PASSWORD_CODE = "admin_forget_password_code_%s";    // 管理员忘记密码验证码
    public static final String EMAIL_LOCK = "email_lock_%s";    // 邮件发送锁

    public static final String CAROUSEL_INFO = "carousel_info_%s";  // 轮播图详情信息
    public static final String ACTIVITY_INFO = "activity_info_%s";  // 活动详情信息
    public static final String BLOG_INFO = "blog_info_%s";      // 文章详情信息
}
