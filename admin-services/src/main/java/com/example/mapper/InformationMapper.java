package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Information;
import com.example.vo.InforQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InformationMapper extends BaseMapper<Information> {
    List<Information> getAll(InforQueryVo vo);

    Information getById(@Param("id") int id);
}
