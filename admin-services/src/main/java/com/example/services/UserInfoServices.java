package com.example.services;

import com.example.entity.Family;
import com.example.entity.UserInfo;
import com.example.mapper.UserInfoMapper;
import com.example.vo.UserInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServices {
    @Resource
    private UserInfoMapper userInfoMapper;

    public List<UserInfo> getUsers(UserInfoVo vo) {
        return this.userInfoMapper.getUsers(vo);
    }

    public List<Family> getFamily(int id) {
        return this.userInfoMapper.getFamily(id);
    }
}
