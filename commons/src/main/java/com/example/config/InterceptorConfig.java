package com.example.config;

import com.example.utils.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Interceptor interceptor = new Interceptor();
        List<String> patterns = new ArrayList<>();
        patterns.add("/admin/**");
        patterns.add("/admin-image/**");
        patterns.add("/user/**");
        patterns.add("/Image/**");
        patterns.add("/admin-dict/**");
        patterns.add("/admin-dish/**");
        patterns.add("/admin-orders/**");
        patterns.add("/user-dish/**");
        patterns.add("/user-information/**");
        patterns.add("/admin-notice/**");
        patterns.add("/admin-activity/**");
        patterns.add("/admin-quiz/**");
        patterns.add("/admin-information/**");
        patterns.add("/admin-health-data/**");

        List<String> allowPatterns = new ArrayList<>();
        allowPatterns.add("swagger-ui.html");
        allowPatterns.add("/admin/admin-login");
        allowPatterns.add("/admin-image/get/**");
        allowPatterns.add("/user/login");
        allowPatterns.add("/user/insert");
        allowPatterns.add("/user-image/**");
        allowPatterns.add("/user-notice/**");
        registry.addInterceptor(interceptor).addPathPatterns(patterns).excludePathPatterns(allowPatterns);
    }
}
