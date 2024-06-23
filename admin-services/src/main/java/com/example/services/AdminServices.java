package com.example.services;

import com.example.baseEntity.LoginMsgVo;
import com.example.entity.Admin;
import com.example.entity.OperationLog;
import com.example.mapper.AdminMapper;
import com.example.mapper.OperationLogMapper;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.JwtUtils;
import com.example.utils.MD5Util;
import com.example.utils.UserUtils;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AdminServices {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;

    public Map<String, String> Login(LoginMsgVo vo) {
//        String password = MD5Util.MD5(vo.getPassword());
        Map<String, String> user = new HashMap<>();
        Optional<Admin> userMsgOptional = Optional.ofNullable(adminMapper.Login(vo.getAccount(), vo.getPassword()));
        if (userMsgOptional.isPresent()) {
            Admin userMsg = userMsgOptional.get();
            user.put("username", userMsg.getUsername());
            user.put("token", JwtUtils.CreateToken(userMsg.getUsername()));
            log.info(String.format("%s用户登陆系统", userMsg.getUsername()));
            String operation = String.format(Const.LOGIN);
            OperationLog operationLog = this.iLogServices.write(userMsg.getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return user;
        } else {
            return null;
        }
    }

    public boolean Logout(String username) {
        log.info(String.format("%s退出登录", username));
        return true;
    }

    public List<Admin> getAll() {
        List<Admin> lists = this.adminMapper.getAll();
        return lists;
    }

    public Boolean insert(Admin admin) {
        if (admin != null) {
            String operation = String.format(Const.ADD+Const.ADMIN);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return this.adminMapper.insert(admin) > 0;
        }
        return false;
    }

    public Boolean delete(int id) {
        Admin admin = this.adminMapper.selectById(id);
        if (admin != null) {
            String operation = String.format(Const.DELETE+Const.ADMIN);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return this.adminMapper.deleteById(id) > 0;
        }
        return false;
    }

    public List<Admin> findByName(String name) {
        return this.adminMapper.findByName(name);
    }

    public Boolean update(Admin admin) {
        if (admin != null) {
            String operation = String.format(Const.UPDATE+Const.ADMIN+admin.getUsername());
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return this.adminMapper.updateById(admin) > 0;
        }
        return false;
    }
}
