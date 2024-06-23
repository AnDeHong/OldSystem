package com.example.services;

import com.example.entity.Notice;
import com.example.entity.OperationLog;
import com.example.entity.Package;
import com.example.mapper.NoticeMapper;
import com.example.mapper.OperationLogMapper;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import com.example.vo.NoticeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NoticesServices {
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;

    public List<Notice> getAll(NoticeVo vo) {
        List<Notice> list = this.noticeMapper.getAll(vo);
        if (list.size() > 0) {
            for (Notice notice: list) {
                Date time = new Date();
                Calendar currentCal = Calendar.getInstance();
                currentCal.setTime(time);

                Calendar noticeCal = Calendar.getInstance();
                noticeCal.setTime(notice.getCreatedTime());

                // 计算两个日期之间的毫秒差
                long millisDiff = noticeCal.getTimeInMillis() - currentCal.getTimeInMillis();

                // 将毫秒差转换为天数差
                long daysDifference = millisDiff / (24 * 60 * 60 * 1000);
                if (daysDifference > 5) {
                    notice.setStatus("已过期");
                    this.noticeMapper.updateById(notice);
                }
            }
        }
        return list;
    }

    public Boolean insert(NoticeVo notice) {
        Notice tar = new Notice();
        if (notice != null) {
            tar.init(UserUtils.getUser());
            BeanUtils.copyProperties(notice,tar);
            tar.setStatus("在使用");
            this.noticeMapper.insert(tar);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),"发布了公告");
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }

    public Boolean delete(int id) {
        Notice notice = this.noticeMapper.selectById(id);
        if (notice != null) {
            notice.setDeleteMark(Const.YES);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),"删除了公告");
            this.operationLogMapper.insert(operationLog);
            return this.noticeMapper.updateById(notice)>0;
        }
        return false;
    }

    public Boolean update(Notice notice) {
        if (notice != null) {
            this.noticeMapper.updateById(notice);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),"更新了公告");
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }
}
