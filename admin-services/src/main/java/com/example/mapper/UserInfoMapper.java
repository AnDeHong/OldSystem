package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Family;
import com.example.entity.UserInfo;
import com.example.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<UserInfo> getUsers(UserInfoVo vo);
    List<Family> getFamily(@Param("userId") int userId);
}
