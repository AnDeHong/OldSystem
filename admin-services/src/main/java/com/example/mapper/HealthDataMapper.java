package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.HealthData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HealthDataMapper extends BaseMapper<HealthData> {
    List<HealthData> getAll(@Param("username") String username);

    HealthData getLast();

    List<HealthData> getAllByIndex(@Param("index") int index);
}
