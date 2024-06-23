package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Family;
import com.example.entity.UserInfo;
import com.example.vo.DishVo;
import com.example.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
    @Select("select * from userinfo where openid=#{openid}")
    UserInfo getUserByOpenId(String openid);
    UserInfoVo getUserInfos(@Param("userId") int userId);

    List<Family> getFamily(@Param("userId") int userId);
    List<UserInfo> getUserList(@Param("userId") int userId);
}
