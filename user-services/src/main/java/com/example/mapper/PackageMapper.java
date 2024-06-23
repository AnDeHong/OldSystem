package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Package;
import com.example.vo.PackageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PackageMapper extends BaseMapper<Package> {
    List<PackageVo> getAll(@Param("tag") String tag);

    Package getById(@Param("packageNo") String packageNo);
}
