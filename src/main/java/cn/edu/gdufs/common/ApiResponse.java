package cn.edu.gdufs.common;

import cn.edu.gdufs.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 封装统一返回结果类
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
@Data
public class ApiResponse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    private ApiResponse() {
    }

    private ApiResponse(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    /**
     * 操作成功响应
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<T>(ResultCode.SUCCESS.getCode(), message);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 操作失败响应
     */
    public static <T> ApiResponse<T> fail() {
        return new ApiResponse<T>(ResultCode.FAILURE.getCode(), ResultCode.FAILURE.getMsg());
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<T>(ResultCode.FAILURE.getCode(), message);
    }

    /**
     * Token异常响应
     */
    public static <T> ApiResponse<T> tokenError() {
        return new ApiResponse<T>(ResultCode.NOT_AUTHORIZATION.getCode(), ResultCode.NOT_AUTHORIZATION.getMsg());
    }

    public static <T> ApiResponse<T> tokenError(String message) {
        return new ApiResponse<T>(ResultCode.NOT_AUTHORIZATION.getCode(), message);
    }

    /**
     * 参数校验异常响应
     */
    public static <T> ApiResponse<T> paramError() {
        return new ApiResponse<T>(ResultCode.VALIDATE_FAILURE.getCode(), ResultCode.VALIDATE_FAILURE.getMsg());
    }

    public static <T> ApiResponse<T> paramError(String message) {
        return new ApiResponse<T>(ResultCode.VALIDATE_FAILURE.getCode(), message);
    }

    /**
     * 权限不足响应
     */
    public static <T> ApiResponse<T> permissionError() {
        return new ApiResponse<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg());
    }

    public static <T> ApiResponse<T> permissionError(String message) {
        return new ApiResponse<T>(ResultCode.FORBIDDEN.getCode(), message);
    }

    /**
     * 服务器内部错误响应
     */
    public static <T> ApiResponse<T> serverError() {
        return new ApiResponse<T>(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMsg());
    }

    public static <T> ApiResponse<T> serverError(String message) {
        return new ApiResponse<T>(ResultCode.SERVER_ERROR.getCode(), message);
    }
}
