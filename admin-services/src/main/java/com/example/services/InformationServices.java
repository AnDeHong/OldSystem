package com.example.services;

import com.example.entity.Dish;
import com.example.entity.Information;
import com.example.entity.OperationLog;
import com.example.mapper.InformationMapper;
import com.example.mapper.OperationLogMapper;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import com.example.vo.InforQueryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class InformationServices {

    @Resource
    private InformationMapper informationMapper;

    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;

    public List<Information> getAll(InforQueryVo vo) {
        return this.informationMapper.getAll(vo);
    }

    public Boolean insert(Information information) {
        if (information != null) {
            information.init(UserUtils.getUser());
            information.setReadTimes(0);
            this.informationMapper.insert(information);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),"添加了资讯文章");
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }

    public Boolean update(Information information) {
        if (information != null) {
            information.init(UserUtils.getUser().getUsername(),new Date());
            this.informationMapper.updateById(information);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),"修改了资讯文章");
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }
    public Boolean delete(int id) {
        Information information = this.informationMapper.getById(id);
        if (information != null) {
            information.setDeleteMark(Const.YES);
            this.informationMapper.updateById(information);
            return true;
        }
        return false;
    }

    public Boolean updateStatus(Information information) {
        if (information.getStatus().equals("上架")) {
            information.init(UserUtils.getUser().getUsername(), new Date());
            information.setStatus(Const.NOSHOW);
            if (this.informationMapper.updateById(information) > 0) {
                String operation = String.format(Const.UPDATE + Const.DISH + Const.STATUS);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(), operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        if (information.getStatus().equals("下架")) {
            information.init(UserUtils.getUser().getUsername(), new Date());
            information.setStatus(Const.SHOW);
            if (this.informationMapper.updateById(information) > 0) {
                String operation = String.format(Const.UPDATE + Const.DISH + Const.STATUS);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(), operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        return false;
    }
}
