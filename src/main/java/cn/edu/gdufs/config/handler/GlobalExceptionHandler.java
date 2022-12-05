package cn.edu.gdufs.config.handler;

import cn.edu.gdufs.common.ApiResponse;
import cn.edu.gdufs.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;

/**
 * Description: 全局异常处理
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ApiException.class)
    public ApiResponse<Object> apiExceptionResult(ApiException e) {
        return ApiResponse.fail(e.getMessage());
    }

    /**
     * 处理参数校验异常异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> validationErrorResult(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        if (result.getFieldErrorCount() > 0) {
            for (FieldError fieldError : result.getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage()).append("；");
            }
        }
        return ApiResponse.paramError(String.format("参数校验错误。[%s]", sb.delete(sb.length() - 1, sb.length())));
    }

    /**
     * 处理参数校验异常异常
     */
    @ExceptionHandler(ValidationException.class)
    public ApiResponse<Object> validationExceptionResult(ValidationException e) {
        return ApiResponse.paramError(String.format("参数校验错误。[%s]", e.getMessage()));
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> exceptionResult(Exception e) {
        log.error("SERVER ERROR: " + e.getMessage());
        return ApiResponse.serverError(String.format("操作失败，请重试。[%s]", e.getMessage()));
    }
}
