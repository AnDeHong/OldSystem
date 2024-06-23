package com.example.services;

import com.example.entity.*;
import com.example.entity.Package;
import com.example.mapper.*;
import com.example.services.Iservices.IDishOrderServices;
import com.example.services.Iservices.IDishServices;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.DateUtils;
import com.example.utils.UserUtils;
import com.example.vo.OrderGetVo;
import com.example.vo.OrdersVo;
import com.example.vo.AddDishOrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OrdersServices {
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;
    @Resource
    private IDishOrderServices iDishOrderServices;
    @Resource
    private DishOrderMapper dishOrderMapper;
    @Resource
    private PackageMapper packageMapper;
    @Resource
    private IDishServices iDishServices;

    public List<Dish> getDishesByName(String name) {
        return this.ordersMapper.getByDishName(name);
    }
    public Boolean insert(AddDishOrderVo vo) {
        DishOrder orders = this.ordersMapper.getDishOrderById(vo.getDishId());
        if (orders != null) {
            orders.setOrderId(vo.getOrderId());
            orders.setTotal(vo.getNum());
            orders.init(UserUtils.getUser());
            if (this.dishOrderMapper.insert(orders) > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Orders or = this.ordersMapper.getById(vo.getOrderId());
                or.setDishIds(String.format("%s,%s",or.getDishIds(),orders.getDishNo()));
                or.setPrice(or.getPrice() + orders.getDiscountsPrice()*orders.getTotal());
                this.ordersMapper.updateById(or);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * DishOrder数据的价格要通过用户端添加订单时设置，此处只获取数据
     * @return
     */
    public List<OrdersVo> getOrders() {
        List<OrdersVo> target = this.ordersMapper.getOrders();
        List<DishOrder> list = new ArrayList<>();
        if (target != null && !target.isEmpty()) {
            for (OrdersVo ordersVo: target) {
                String[] ids = ordersVo.getDishIds().split(",");
                if (ids.length == 1) {
                    Package pa = this.packageMapper.getById(ids[0]);
                    if (pa != null) {
                        String[] dishIds = pa.getDishIds().split(",");
                        List<Dish> listDish = this.iDishServices.getByNo(dishIds);
                        for (Dish dish: listDish) {
                            DishOrder dishOrder = new DishOrder();
                            BeanUtils.copyProperties(dish,dishOrder);
                            list.add(dishOrder);
                        }
                    } else {
                        list = this.iDishOrderServices.getById(ids);
                    }
                } else {
                    list = this.iDishOrderServices.getById(ids);
                }
                ordersVo.setDishes(list);
            }
        }
        return target;
    }

    public List<OrdersVo> getOrderBy(OrderGetVo vo) {
        List<OrdersVo> target = this.ordersMapper.getOrdersBy(vo);
        if (target != null && !target.isEmpty()) {
            for (OrdersVo ordersVo: target) {
                List<DishOrder> list = new ArrayList<>();
                String[] ids = ordersVo.getDishIds().split(",");
                if (ids.length == 1) {
                    Package pa = this.packageMapper.getById(ids[0]);
                    if (pa != null) {
                        String[] dishIds = pa.getDishIds().split(",");
                        List<Dish> listDish = this.iDishServices.getByIds(dishIds);
                        for (Dish dish: listDish) {
                            DishOrder dishOrder = new DishOrder();
                            BeanUtils.copyProperties(dish,dishOrder);
                            list.add(dishOrder);
                        }
                    } else {
                        list = this.iDishOrderServices.getById(ids);
                    }
                } else {
                    list = this.iDishOrderServices.getById(ids);
                }
                ordersVo.setDishes(list);
            }
        }
        return target;
    }

    public Boolean delete(int id) {
        Orders orders = this.ordersMapper.selectById(id);
        if (orders != null) {
            orders.setDeleteMark(Const.YES);
            orders.init(UserUtils.getUser().getUsername(),new Date());
            if (this.ordersMapper.updateById(orders) > 0) {
                String operation = String.format(Const.DELETE+Const.ORDER);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
            return false;
        }
        return false;
    }

    public Boolean updateStatus(Orders orders) {
        Orders o = this.ordersMapper.getById(orders.getId());
        if (o != null) {
            o.setStatus(Const.SEND);
            this.ordersMapper.updateById(o);
            String operation = String.format(Const.UPDATE+"订单状态");
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }

    public Boolean update(DishOrder dishOrder) {
        if (this.dishOrderMapper.updateById(dishOrder) > 0) {
            Orders orders = this.ordersMapper.getById(dishOrder.getOrderId());
            orders.setPrice(orders.getPrice() + dishOrder.getDiscountsPrice()*dishOrder.getTotal());
            this.ordersMapper.updateById(orders);
            String operation = String.format(Const.UPDATE+"订单信息");
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }

    public Boolean deleteDishOrder(DishOrder dishOrder) {
        Orders orders = this.ordersMapper.getById(dishOrder.getOrderId());
        if (orders != null) {
            orders.setDishIds(this.iDishOrderServices.getDishOrderIds(orders.getDishIds(),dishOrder.getId()));
            orders.setPrice(orders.getPrice()-dishOrder.getDiscountsPrice()*dishOrder.getTotal());
            this.ordersMapper.updateById(orders);
            dishOrder.setDeleteMark(Const.YES);
            this.dishOrderMapper.updateById(dishOrder);
            return true;
        }
        return false;
    }
}
