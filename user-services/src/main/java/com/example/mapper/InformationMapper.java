package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Information;
import com.example.vo.InformationQueryVo;
import com.example.vo.informationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InformationMapper extends BaseMapper<Information> {

    List<informationVo> getInformation(InformationQueryVo vo);

    Information getById(@Param("id") int id);
}
