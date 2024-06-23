package com.example.utils;

import com.example.baseEntity.UserMsg;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;
import java.util.Random;

@Component
public class UserUtils {
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private HttpServletRequest request;

    public static String generateCode() {
        int length = 6; // 验证码长度
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 验证码字符集

        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }

    public static void sendCode(String email, String code) {
        // 发件人电子邮箱
        String from = "508430810@qq.com";

        // 指定发送邮件的主机为 localhost
        String host = "smtp.qq.com";

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("508430810@qq.com", "pslhimupvqjmbhjj"); //发件人邮件用户名、密码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email));

            // Set Subject: 头部头字段
            message.setSubject("登录验证码");

            // 设置消息体
            message.setText(code);

            // 发送消息
            Transport.send(message);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static UserMsg getUser() {
        return RequestContextHandler.getLoginName();
    }
}
