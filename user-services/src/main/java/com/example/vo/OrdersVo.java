package com.example.vo;

import com.example.entity.DishOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrdersVo {

    /** 订单编号*/
    private String orderNo;
    private Integer id;
    /** 用户标识 */
    private Integer userId ;
    /** 地址信息 */
    private String address ;
    /** 价格 */
    private Float price ;
    /** 配送员 */
    private Integer sendId ;
    /** 下单时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date orderDate ;
    /** 送达时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date reachDate ;
    /** 数量 */
    private Integer num ;
    /** 状态：已完成，取餐，配送中，未完成 */
    private String status;
    /** 评价 */
    private String evaluate;
    /** 备注 */
    private String notes ;
    /** 菜品ids */
    private String dishIds ;
    private String username;
    /** 预定时间 */
    private String chooseDate;

    private List<DishOrder> dishes;
}
