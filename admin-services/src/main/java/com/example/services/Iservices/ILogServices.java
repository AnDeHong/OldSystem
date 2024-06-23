package com.example.services.Iservices;

import com.example.entity.OperationLog;


public interface ILogServices {
    OperationLog write(String username, String operation);
}
