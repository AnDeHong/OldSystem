package com.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.DictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {

    @Select("select * from dict_type where dict_code = #{type} and delete_mark = 'N'")
    List<DictType> getAllType(String type);
}
