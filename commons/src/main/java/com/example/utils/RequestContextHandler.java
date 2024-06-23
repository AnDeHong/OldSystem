package com.example.utils;

import com.example.baseEntity.UserMsg;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestContextHandler {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static final String TOKEN = "TOKEN";
    private static final String OPENID = "OPENID";


    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static boolean has(String key) {
        return null != threadLocal.get() && null != threadLocal.get().get(key);
    }

    public static UserMsg getUser() {
        if (has(TOKEN)) {
            UserMsg userMsg = new UserMsg();
            userMsg.setToken(get(TOKEN).toString());
            userMsg.setUsername(JwtUtils.getUser(get(TOKEN).toString()));
            return userMsg;
        } else if (has(OPENID)) {
            UserMsg userMsg = new UserMsg();
            userMsg.setToken(get(OPENID).toString());
            return userMsg;
        } else {
            return new UserMsg();
        }
    }
    public static UserMsg getLoginName() {
        return getUser();
    }
    public static void setToken(String token) {
        set(TOKEN, token);
    }
    public static void setOpenId(String openid) {
        set(OPENID, openid);
    }
}
