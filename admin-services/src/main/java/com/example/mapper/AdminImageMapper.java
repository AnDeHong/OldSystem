package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.AdminImage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AdminImageMapper extends BaseMapper<AdminImage> {
    @Select("select * from admin_image where delete_mark = 'N'")
    List<AdminImage> getAll();

    @Select("select * from admin_image where uuid = #{uuid} and delete_mark = 'N'")
    AdminImage getImageByUuid(String uuid);

    List<AdminImage> getByParams(@Param("code") String code, @Param("name") String name, @Param("status") String status);

}
