package com.example.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtils {
    //    todo 过期时间
    private static long expireTime = 604800;
    //    todo 密钥
    private static String secret = "AnDeHongabcdefjabcdefjabcdefgabcabcdefgabcdefgabcdeAnDeHongabcdefjabcdefjabcdefgabcabcdefgabcdefgabcde";

    //    todo 生成token
    public static String CreateToken(String username) {
        Date date = new Date();
        Date expireAtion = new Date(date.getTime() + 1000*expireTime);
        String refreshToken = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(expireAtion)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        // Include refresh token in the JWT payload
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(expireAtion)
                .claim("refreshToken", refreshToken)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //    todo 解析token
    public static String getUser(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            log.error("JWT解析异常: {}", e.getMessage());
            return null;
        } catch (IllegalArgumentException e)
        {
            log.error("传入的token为空或格式不正确: {}", e.getMessage());
            return null;
        }
    }
}