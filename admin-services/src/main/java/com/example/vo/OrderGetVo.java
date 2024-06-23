package com.example.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderGetVo {
    private String username;
    private String phone;
    private String orderId;
    private String status;
    private String start;
    private String end;
}
