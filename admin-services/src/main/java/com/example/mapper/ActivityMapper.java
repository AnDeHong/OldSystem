package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Activity;
import com.example.vo.ActivityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    List<Activity> getAll(ActivityVo vo);

    Activity getById(@Param("id") int id);
}
