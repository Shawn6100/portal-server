package cn.edu.gdufs.service;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/9
 */
public interface CommonService {

    // 管理员忘记密码发送验证码
    void adminForgetPasswordSendEmail(String email);

    // 管理员忘记密码修改为新密码
    void adminForgetPassword(String email, String code, String newPassword);
}
