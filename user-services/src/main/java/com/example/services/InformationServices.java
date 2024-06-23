package com.example.services;

import com.example.entity.Information;
import com.example.mapper.InformationMapper;
import com.example.vo.InformationQueryVo;
import com.example.vo.informationVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class InformationServices {

    @Resource
    private InformationMapper informationMapper;

    public List<informationVo> getInformation(InformationQueryVo vo) {
        if (vo.getType().equals("1")) {
            vo.setType("");
            List<informationVo> list = this.informationMapper.getInformation(vo);
            List<informationVo> target = new ArrayList<>();
            for (informationVo infor: list) {
                if (vo.getTag().contains(infor.getTag())) {
                    infor.setImgUrls(infor.getImgUrl().split(","));
                    target.add(infor);
                }
            }
            return target;
        }
        else {
            List<informationVo> list = this.informationMapper.getInformation(vo);
            List<informationVo> targetList = new ArrayList<>();
            for (informationVo infor: list) {
                infor.setImgUrls(infor.getImgUrl().split(","));
                targetList.add(infor);
            }
            return targetList;
        }
    }

    public Boolean updateReadTimes(int id) {
        Information information = this.informationMapper.getById(id);
        if (information != null) {
            information.setReadTimes(information.getReadTimes() + 1);
            this.informationMapper.updateById(information);
            return true;
        }
        return false;
    }
}
