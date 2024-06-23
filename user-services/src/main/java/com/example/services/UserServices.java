package com.example.services;

import cn.hutool.json.JSONObject;
import com.example.entity.Family;
import com.example.entity.UserInfo;
import com.example.mapper.FamilyMapper;
import com.example.mapper.UserMapper;
import com.example.response.TaskException;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import com.example.vo.UserInfoVo;
import com.example.vo.WxInfoVo;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServices {
    @Resource
    private UserMapper userMapper;

    @Resource
    private FamilyMapper familyMapper;

    public Boolean perfectInfo(UserInfo user) {
        UserInfo userInfo = this.userMapper.selectById(user.getId());
        if (userInfo != null) {
            if(user.getAge() != null) {
                userInfo.setAge(user.getAge());
            }
            if(!user.getPhone().isEmpty()) {
                userInfo.setPhone(user.getPhone());
            }
            if(!user.getUsername().isEmpty()) {
                userInfo.setUsername(user.getUsername());
            }
            if(!user.getEmail().isEmpty()) {
                userInfo.setEmail(user.getEmail());
            }
            if(!user.getTag().isEmpty()) {
                userInfo.setTag(user.getTag());
            }
            user.init(user.getUsername(), new Date());
            return this.userMapper.updateById(userInfo) > 0;
        }
        return false;
    }
    public String getSessionKey(String code) {
        // 假设这里是调用微信接口获取用户session_key的逻辑
        String appid = "wx848ad0b139f2a578";
        String secret = "251a098ce98a9fca43d36cf4c36bf2e7";
        String grantType = "authorization_code";

        // 构建微信接口的URL
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + grantType;

        // 发起HTTP请求，获取openid和session_key
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        JSONObject jsonResult = new JSONObject(result);
        String openid = jsonResult.get("openid", String.class);
        return openid;
    }
    public UserInfo login(WxInfoVo vo) {
        String openid = this.getSessionKey(vo.getCode());
        log.info("用户openid:" + openid);

        // 根据openid查询用户信息
        if (StringUtils.isNullOrEmpty(openid)) {
            return null;
        }
        UserInfo userInfo = userMapper.getUserByOpenId(openid);
        if (userInfo != null) {
            // 用户已存在，直接返回用户信息
            userInfo.setUsername(vo.getUsername());
            userInfo.setAvatarUrl(vo.getAvatarUrl());
            this.userMapper.updateById(userInfo);
            return userInfo;
        } else {
            // 用户不存在，创建新用户
            UserInfo newUser = new UserInfo();
            newUser.setOpenid(openid);
            if (vo.getSex() == 0) {
                newUser.setSex("男");
            } else if (vo.getSex() == 1) {
                newUser.setSex("女");
            } else {
                newUser.setSex("");
            }
            newUser.setUsername(vo.getUsername());
            newUser.setAvatarUrl(vo.getAvatarUrl());
            newUser.setDeleteMark(Const.NO);
            newUser.setCreatedTime(new Date());
            userMapper.insert(newUser);
            return newUser;
        }
    }

    public boolean Logout(String username) {
        log.info(String.format("%s退出登录", username));
        return true;
    }

    public UserInfoVo getUserInfos(int userId) {
        UserInfoVo userInfoVo = this.userMapper.getUserInfos(userId);
        if (userInfoVo != null) {
            List<Family> families = this.familyMapper.getFamily(userInfoVo.getId());
            userInfoVo.setFamilyList(families);
            return userInfoVo;
        }
        return null;
    }

    public List<Family> getFamily(int id) {
        return this.userMapper.getFamily(id);
    }
    public List<UserInfo> getUserList(int id) {
        return this.userMapper.getUserList(id);
    }
}
