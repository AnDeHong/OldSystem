package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("select * from notice where delete_mark = 'N' ")
    List<Notice> getAll();

    Notice getLast();
}
