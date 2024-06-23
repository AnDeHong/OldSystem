package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.OperationLog;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    @Select("select * from operationlog where time >= #{date} order by time desc")
    List<OperationLog> getAll(LocalDate date);
}
