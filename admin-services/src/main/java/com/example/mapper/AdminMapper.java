package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Admin;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from admin where username=#{user} and password=#{password}")
    public Admin Login(String user, String password);

    @Select("select * from admin")
    List<Admin> getAll();

    @Select("SELECT * FROM admin WHERE username LIKE CONCAT('%', #{user}, '%')")
    List<Admin> findByName(String user);

    @Select("select * from admin where username=#{username}")
    Admin getByUserName(String username);
}
