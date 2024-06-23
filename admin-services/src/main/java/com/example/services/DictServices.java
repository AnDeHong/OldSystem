package com.example.services;


import com.example.entity.Dict;
import com.example.entity.OperationLog;
import com.example.mapper.DictMapper;
import com.example.mapper.OperationLogMapper;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import com.example.vo.DictTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictServices {
    @Resource
    private DictMapper dictMapper;
    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;

    public List<Dict> getDictType() {
        return this.dictMapper.getDictType();
    }

    public Boolean insert(Dict dict) {
        dict.init(UserUtils.getUser());
        if (this.dictMapper.insert(dict) > 0) {
            String operation = String.format(Const.ADD+Const.DICT);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return true;
        } else {
            return false;
        }
    }
    public String[] getValues(String type) {
        return this.dictMapper.getValues(type);
    }

    public String[] getDictCode() {
        return this.dictMapper.getDictCode();
    }

    public List<DictTypeVo> getDictName(String code) {
        return this.dictMapper.getDictName(code);
    }

}
