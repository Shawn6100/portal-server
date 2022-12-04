package cn.edu.gdufs.config.handler;

import cn.edu.gdufs.common.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Description: 封装返回值处理
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
public class MethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler returnValueHandler;

    public MethodReturnValueHandler(HandlerMethodReturnValueHandler returnValueHandler) {
        this.returnValueHandler = returnValueHandler;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return this.returnValueHandler.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 判断外层是否由ApiResponse包裹
        if (returnValue instanceof ApiResponse) {
            this.returnValueHandler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        }
        this.returnValueHandler.handleReturnValue(ApiResponse.success(returnValue), returnType, mavContainer, webRequest);
    }
}
