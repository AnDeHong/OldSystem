package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.ActivitySign;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ActivitySignMapper extends BaseMapper<ActivitySign> {
    @Select("select * from activity_sign where activity_id = #{id}")
    List<ActivitySign> getByActivityId(int id);
}
