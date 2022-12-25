package cn.edu.gdufs.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: Controller基类
 * Author: 严仕鹏
 * Date: 2022/12/4
 */
public class BaseController {

    /**
     * 获取目前用户的id和role
     * @return 用户id和用户权限Map
     */
    private Map<String, Object> getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Map<String, Object> result = new HashMap<>();
        result.put("userId", request.getAttribute("userId"));
        result.put("role", request.getAttribute("role"));
        return result;
    }

    /**
     * 获取当前用户的id
     */
    public int getUserId() {
        return (int) getCurrentUser().get("userId");
    }

    /**
     * 获取当前用户的权限
     */
    public int getUserRole() {
        return (int) getCurrentUser().get("role");
    }

}
