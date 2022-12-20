package cn.edu.gdufs.constant;

/**
 * Description: 缓存中key的模板
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
public class CacheConstant {
    private CacheConstant() {}

    public static final long EXPIRE_TIME = 60 * 60 * 3;         // 通用缓存时间：三小时
    public static final long SHORT_EXPIRE_TIME = 30;            // 防止缓存穿透过期时间，三十秒

    public static final String TOKEN_KEY = "user_token_%s";     // 用户token
    public static final String TOKEN_INFO = "%s_%s";            // token存储的信息
    public static final String ADMIN_INFO = "admin_info_%s";    // 管理员用户详情信息
    public static final String ADMIN_USER_INFO = "admin_user_info_%s"; // 管理员获取前台用户的详情信息

    public static final String ADMIN_FORGET_PASSWORD_CODE = "admin_forget_password_code_%s";    // 管理员忘记密码验证码
    public static final String USER_REGISTER_CODE = "user_register_code_%s";
    public static final String EMAIL_LOCK = "email_lock_%s";    // 邮件发送锁

    public static final String CAROUSEL_INFO = "carousel_info_%s";  // 轮播图详情信息
    public static final String ACTIVITY_INFO = "activity_info_%s";  // 活动详情信息
    public static final String BLOG_INFO = "blog_info_%s";          // 文章详情信息
    public static final String LECTURE_INFO = "lecture_info_%s";    // 分享会详情信息

    public static final String LECTURE_SIGNUP_REMAINING_CAPACITY = "lecture_remaining_number_%s"; // 分享会剩余报名容量
    public static final String LECTURE_SIGNUP_USER_LIST = "lecture_signup_success_user_list_%s"; // 分享会报名用户id列表
}
