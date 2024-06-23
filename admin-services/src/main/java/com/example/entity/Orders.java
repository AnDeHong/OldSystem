package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
@TableName("orders")
public class Orders extends BaseEntity {
    /** 订单编号*/
    private String orderNo;
    /** 用户标识 */
    private Integer userId ;
    /** 地址信息 */
    private Integer addressId ;
    /** 菜品ids */
    private String dishIds ;
    /** 价格 */
    @Column(precision = 10)
    private Float price ;
    /** 配送员 统一配送员配送*/
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
    /** 预定时间 */
    private String chooseDate;
    /** 预定就餐开始时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startDate ;
    /** 预定就餐截止时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endDate ;
}
