package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.AdminImage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserImageMapper extends BaseMapper<AdminImage> {
    @Select("select im.* from admin_image im where im.delete_mark='N' and im.is_show='上架' and im.code=#{code}")
    List<AdminImage> getUserImage(String code);

    @Select("select * from admin_image where uuid = #{uuid} and delete_mark = 'N'")
    AdminImage getImageByUuid(String uuid);

    AdminImage getByNameAndCode(@Param("name") String name,@Param("code") String code);
}
