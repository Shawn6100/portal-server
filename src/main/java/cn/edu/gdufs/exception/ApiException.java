package cn.edu.gdufs.exception;

/**
 * Description: 自定义异常类
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
