package com.example.services;

import com.example.entity.Activity;
import com.example.entity.ActivitySign;
import com.example.entity.OperationLog;
import com.example.entity.UserInfo;
import com.example.mapper.ActivityMapper;
import com.example.mapper.ActivitySignMapper;
import com.example.mapper.OperationLogMapper;
import com.example.mapper.UserInfoMapper;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.DateUtils;
import com.example.utils.UserUtils;
import com.example.vo.ActivityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

@Service
public class ActivityServices {

    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private ActivitySignMapper activitySignMapper;

    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;

    @Resource
    private UserInfoMapper userInfoMapper;

    public List<Activity> getAll(ActivityVo vo) {
        if (vo.getStartDate().equals("Invalid date")) {
            vo.setStartDate("");
        }
        if (vo.getEndDate().equals("Invalid date")) {
            vo.setEndDate("");
        }
        List<Activity> list = this.activityMapper.getAll(vo);
        Date date = new Date();
        for (Activity ac: list) {
            long time = (ac.getStartDate().getTime() - date.getTime())*60*60 * 1000;
            if (date.compareTo(ac.getStartDate()) >= 0 && date.compareTo(ac.getEndDate()) <= 0) {
                ac.setStatus("活动中");
                this.activityMapper.updateById(ac);
            } else if (date.compareTo(ac.getEndDate()) > 0) {
                ac.setStatus("已结束");
                this.activityMapper.updateById(ac);
            } else if (time >= 120 && date.compareTo(ac.getStartDate()) < 0) {
                ac.setStatus("报名中");
                this.activityMapper.updateById(ac);
            } else if(time < 120 && date.compareTo(ac.getStartDate()) < 0) {
                ac.setStatus("未开始");
                this.activityMapper.updateById(ac);
            }
        }
        return this.activityMapper.getAll(vo);
    }

    public Boolean insert(ActivityVo vo) throws ParseException {
        Activity activity = new Activity();
        if (vo != null) {
            activity.init(UserUtils.getUser());
            activity.setStartDate(DateUtils.dateFormat(vo.getStartDate()));
            activity.setEndDate(DateUtils.dateFormat(vo.getEndDate()));
            activity.setName(vo.getName());
            activity.setContent(vo.getContent());
            activity.setType(vo.getType());
            activity.setOther(vo.getOther());
            activity.setAddress(vo.getAddress());
            activity.setScore(vo.getScore());
            activity.setHead(vo.getHead());
            this.activityMapper.insert(activity);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),"发布了活动");
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }

    public Boolean update(Activity activity) {
        if (this.activityMapper.updateById(activity) > 0) {
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),"发布了活动");
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }

    public Boolean delete(int id) {
        Activity activity = this.activityMapper.getById(id);
        if (activity != null) {
            activity.setDeleteMark(Const.YES);
            this.activityMapper.updateById(activity);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),"删除了活动");
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }
//获取参加活动的男女人数
    public List<Map<String, Object>> getUserNum() {
        List<Map<String,Object>> res = new ArrayList<>();
        List<Activity> list = this.activityMapper.getAll(new ActivityVo());
        for (Activity activity: list) {
            List<ActivitySign> activitySigns = this.activitySignMapper.getByActivityId(activity.getId());
            int manNum = 0;
            int womanNum = 0;
            for (ActivitySign activitySign: activitySigns) {
                UserInfo userInfo = this.userInfoMapper.selectById(activitySign.getUserId());
                if (userInfo.getSex().equals("男")){
                    manNum++;
                } else {
                    womanNum++;
                }
            }
            Map<String,Object> map = new HashMap<>();
            int[] arr = new int[2];
            arr[0] = manNum;
            arr[1] = womanNum;
            map.put("name", activity.getName());
            map.put("manNum", manNum);
            map.put("womanNum", womanNum);
            res.add(map);
        }
        return res;
    }
}
