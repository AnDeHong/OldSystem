package com.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Dict;
import com.example.vo.DictTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
    @Select("select value from dict_type where dict_code = #{type} and delete_mark = 'N'")
    String[] getValues(String type);

    @Select("select * from dict where delete_mark = 'N'")
    List<Dict> getDictType();

    @Select("select code from dict where delete_mark = 'N'")
    String[] getDictCode();

    List<DictTypeVo> getDictName(@Param("code") String code);


}
