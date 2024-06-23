package com.example.services;

import com.example.entity.Activity;
import com.example.entity.ActivitySign;
import com.example.mapper.ActivityMapper;
import com.example.mapper.ActivitySignMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ActivityServices {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivitySignMapper activitySignMapper;

    public List<Map<String, Object>> getAll() {
        List<Activity> list = this.activityMapper.getAll();
        List<Map<String, Object>> target = new ArrayList<>();
        Date date = new Date();
        for (Activity activity: list) {
            long time = (activity.getStartDate().getTime() - date.getTime())*60*60 * 1000;
            if (date.compareTo(activity.getStartDate()) >= 0 && date.compareTo(activity.getEndDate()) <= 0) {
                activity.setStatus("活动中");
                this.activityMapper.updateById(activity);
            } else if (date.compareTo(activity.getEndDate()) > 0) {
                activity.setStatus("已结束");
                this.activityMapper.updateById(activity);
            } else if (time >= 120 && date.compareTo(activity.getStartDate()) < 0) {
                activity.setStatus("报名中");
                this.activityMapper.updateById(activity);
            } else if(time < 120 && date.compareTo(activity.getStartDate()) < 0) {
                activity.setStatus("未开始");
                this.activityMapper.updateById(activity);
            }
            ActivitySign activitySign = this.activitySignMapper.selectById(activity.getId());
            if (activitySign == null && activity.getStatus().equals("报名中")) {
                Map<String, Object> res = new HashMap<>();
                res.put("res", activity);
                res.put("isSign", 1);
                target.add(res);
            } else if (activitySign != null) {
                Map<String, Object> res = new HashMap<>();
                res.put("res", activity);
                res.put("isSign", 2);
                target.add(res);
            } else {
                Map<String, Object> res = new HashMap<>();
                res.put("res", activity);
                res.put("isSign", 3);
                target.add(res);
            }
        }
        return target;
    }

    public Boolean sign(int userId,int id) {
//        Activity activity = this.activityMapper.getById(id);
        ActivitySign activitySign = new ActivitySign();
        activitySign.setUserId(userId);
        activitySign.setActivityId(id);
        activitySign.setSignDate(new Date());
        return this.activitySignMapper.insert(activitySign) > 0;
    }

}
