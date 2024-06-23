package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.DongTai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DongTaiMapper extends BaseMapper<DongTai> {

    @Select("select * from dongtai where user_id = #{userId}")
    DongTai getByUserId(int userId);
}
