package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Notice;
import com.example.vo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    List<Notice> getAll(NoticeVo vo);
}
