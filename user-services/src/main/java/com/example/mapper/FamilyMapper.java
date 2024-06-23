package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Family;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FamilyMapper extends BaseMapper<Family> {

    @Select("select * from family where user_id = #{userId}")
    List<Family> getFamily(int userId);
}
