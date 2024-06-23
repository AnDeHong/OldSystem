package com.example.utils;

import com.example.response.TaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component
@Slf4j
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        log.info("==========请求路径为：" + request.getRequestURI());
        if (token == null || token.isEmpty()) {
            log.error("error token!");
            throw new TaskException("error token!");
        }
        if (request.getRequestURI().contains("admin")) {
            RequestContextHandler.setToken(token);
        } else {
            RequestContextHandler.setOpenId(token);
        }
        return true;
    }



}
