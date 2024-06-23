package com.example.services;

import com.example.entity.Admin;
import com.example.entity.OperationLog;
import com.example.mapper.AdminMapper;
import com.example.mapper.OperationLogMapper;
import com.example.services.Iservices.ILogServices;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class LogServices implements ILogServices {

    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private AdminMapper adminMapper;

    @Override
    public OperationLog write(String username, String operation) {
        Admin admin = this.adminMapper.getByUserName(username);
        return new OperationLog(username,admin.getRole(),new Date(),operation);
    }

    public List<OperationLog> getAll() {
        LocalDate today = LocalDate.now();
        LocalDate monthAgo = today.minusDays(29);
        System.out.println("monthAgo:"+monthAgo);
        return this.operationLogMapper.getAll(monthAgo);
    }
}
