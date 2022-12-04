package cn.edu.gdufs.constant;

/**
 * Description: 响应码和响应结果
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAILURE(400, "操作失败"),
    NOT_AUTHORIZATION(401, "登录失效"),
    VALIDATE_FAILURE(402, "参数检验失败"),
    FORBIDDEN(403, "权限不足"),
    SERVER_ERROR(500, "服务器错误");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
