package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Package;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PackageMapper extends BaseMapper<Package> {
    @Select("select * from package where delete_mark = 'N'")
    List<Package> getAll();

    List<Package> getByStatus(@Param("name") String name, @Param("type") String type, @Param("status") String status);

    Package getById(@Param("packageNo") String packageNo);
}
