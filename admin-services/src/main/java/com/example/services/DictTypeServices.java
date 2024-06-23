package com.example.services;

import com.example.entity.DictType;
import com.example.entity.OperationLog;
import com.example.mapper.DictTypeMapper;
import com.example.mapper.OperationLogMapper;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DictTypeServices {

    @Resource
    private DictTypeMapper dictTypeMapper;
    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;

    public List<DictType> getAllType(String type) {
        return this.dictTypeMapper.getAllType(type);
    }
    public Boolean insert(DictType dictType) {
        dictType.init(UserUtils.getUser());
        if (this.dictTypeMapper.insert(dictType) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean update(DictType dictType) {
        if (dictType != null) {
            dictType.init(UserUtils.getUser().getUsername(),new Date());
            if (this.dictTypeMapper.updateById(dictType) > 0) {
                String operation = String.format(Const.UPDATE+Const.DICTTYPE);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        DictType dictType = this.dictTypeMapper.selectById(id);
        if (dictType != null) {
            dictType.init(UserUtils.getUser().getUsername(),new Date());
            dictType.setDeleteMark(Const.YES);
            String operation = String.format(Const.DELETE+Const.DICTTYPE);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return this.update(dictType);
        }
        return false;
    }
}
