package com.example.services;

import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoticeServices {

    @Resource
    private NoticeMapper noticeMapper;

    public List<Notice> getAllList() {
        return this.noticeMapper.getAll();
    }

    public Notice getLast() {
        return this.noticeMapper.getLast();
    }
}
