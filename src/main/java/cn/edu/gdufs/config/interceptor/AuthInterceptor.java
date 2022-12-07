package cn.edu.gdufs.config.interceptor;

import cn.edu.gdufs.common.ApiResponse;
import cn.edu.gdufs.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 权限拦截器
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
public class AuthInterceptor implements HandlerInterceptor {

    public static final String TOKEN_HEADER = "token";

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        String token = request.getHeader(TOKEN_HEADER);
        if (token == null || token.isEmpty()) {
            response.getWriter().write(JSON.toJSONString(ApiResponse.tokenError("token缺失")));
            return false;
        }
        int role;
        try {
            role = tokenUtil.getRoleByToken(token);
        } catch (Exception e) {
            response.getWriter().write(JSON.toJSONString(ApiResponse.tokenError(e.getMessage())));
            return false;
        }
        if (!hasPermission(handler, role)) {
            response.getWriter().write(JSON.toJSONString(ApiResponse.permissionError()));
            return false;
        }
        int userId = tokenUtil.getIdByToken(token);
        tokenUtil.refreshToken(token, userId);
        request.setAttribute("userId", userId);
        request.setAttribute("role", role);
        return true;
    }

    private boolean hasPermission(Object handler, int role) {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
        if (requiredPermission == null) {
            requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
        }
        if (requiredPermission == null) {
            return true;
        }
        for (int permission : requiredPermission.value()) {
            if (role == permission) {
                return true;
            }
        }
        return false;
    }
}
