package cn.edu.gdufs.config;

import cn.edu.gdufs.config.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    /**
     * 添加权限拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .excludePathPatterns("/admin/login")    // 管理员登录
                .excludePathPatterns("/user/register")  // 用户注册
                .excludePathPatterns("/common/**")      // 通用Controller
                .excludePathPatterns("/upload/**")      // 服务器上的文件资源
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**") // swagger
                .addPathPatterns("/**");
    }

    /**
     * 处理跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // 用户上传文件的路径
    @Value("${my-config.file-upload-path}")
    private String filePath;

    /**
     * 添加资源管理器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + filePath);
    }
}
