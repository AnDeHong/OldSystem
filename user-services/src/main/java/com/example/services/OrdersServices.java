package com.example.services;

import com.example.entity.*;
import com.example.entity.Package;
import com.example.mapper.*;
import com.example.services.IServices.IDishServices;
import com.example.utils.Const;
import com.example.utils.DateUtils;
import com.example.utils.UserUtils;
import com.example.vo.AddOrdersVo;
import com.example.vo.AddPackageVo;
import com.example.vo.OrdersVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

@Service
public class OrdersServices {

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DishOrderMapper dishOrderMapper;
    @Resource
    private IDishServices iDishServices;
    @Resource
    private PackageMapper packageMapper;
    @Resource
    private DongTaiMapper dongTaiMapper;
    @Resource
    private AddressMapper addressMapper;

    public Boolean insert(AddOrdersVo vo) throws ParseException {
        Orders orders = new Orders();
        UserInfo userInfo = this.userMapper.getUserByOpenId(UserUtils.getUser().getToken());
        orders.userInit(userInfo.getUsername());
        orders.setUserId(vo.getUserId());
        orders.setAddressId(vo.getAddressId());
        StringBuilder ids = new StringBuilder();
        Float price = 0.0F;
        Integer num = 0;
        for (Dish dish: vo.getSelectDish()) {
            DishOrder dishOrder = this.dishOrderMapper.getDishById(dish.getId());
            dishOrder.setTotal(dish.getTotal());
            dishOrder.userInit(userInfo.getUsername());
            if (this.dishOrderMapper.insert(dishOrder) > 0) {
                try{
                    Thread.sleep(500);
                    ids.append(dishOrder.getId()).append(",");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            price += dish.getDiscountsPrice()*dish.getTotal();
            num += dish.getTotal();
        }
        orders.setDishIds(ids.toString());
        orders.setPrice(price);
        orders.setOrderDate(new Date());
        orders.setNum(num);
        orders.setStatus(Const.QUCAN);
        orders.setNotes(vo.getNotes());
        orders.setChooseDate(vo.getChooseDate());
        orders.setStartDate(DateUtils.dateFormat1(vo.getStartDate()));
        orders.setEndDate(DateUtils.dateFormat1(vo.getEndDate()));
        if (this.ordersMapper.insert(orders) > 0) {
            orders.setOrderNo(Const.ORDER_NO+orders.getId());
            this.ordersMapper.updateById(orders);
            this.setDongTaiNum(vo.getUserId());
            return true;
        }
        return false;
    }

    public List<OrdersVo> getUserOrders(int id){
        List<OrdersVo> target = this.ordersMapper.getOrders(id);
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
                        list = this.iDishServices.getById(ids);
                    }
                } else {
                    list = this.iDishServices.getById(ids);
                }
                ordersVo.setDishes(list);
            }
        }
        return target;
    }

    public Boolean insertOrders(AddPackageVo vo) {
        Orders orders = new Orders();
        orders.setUserId(vo.getUserId());
        orders.setAddressId(vo.getAddressId());
        orders.setNotes(vo.getNotes());
        Package pa = this.packageMapper.getById(vo.getPackageNo());
        orders.setNum(vo.getNum());
        orders.setPrice(pa.getDiscountPrice()*vo.getNum());
        orders.setStatus(Const.QUCAN);
        orders.setOrderDate(new Date());
        orders.setDishIds(vo.getPackageNo());
        orders.setChooseDate(vo.getChooseDate());
        orders.setStartDate(DateUtils.dateFormat1(vo.getStartDate()));
        orders.setEndDate(DateUtils.dateFormat1(vo.getEndDate()));
        UserInfo userInfo = this.userMapper.getUserByOpenId(UserUtils.getUser().getToken());
        orders.userInit(userInfo.getUsername());
        if (this.ordersMapper.insert(orders) > 0) {
            orders.setOrderNo(Const.ORDER_NO+orders.getId());
            this.ordersMapper.updateById(orders);
            this.setDongTaiNum(vo.getUserId());
            return true;
        }
        return false;
    }

    public Boolean updateEvaluate(int id,String evaluate) {
        Orders orders = this.ordersMapper.selectById(id);
        orders.setEvaluate(evaluate);
        if (this.ordersMapper.updateById(orders) > 0) {
            return true;
        }
        return false;
    }
    public List<OrdersVo> getNewOrder(int userId) {
//        DongTai dongTai = this.dongTaiMapper.getByUserId(userId);
        List<OrdersVo> ordersVos = this.ordersMapper.getAllOrder();
//        if (ordersVos.size()>0) {
//            if (dongTai.getDateTime() == null) {
//                dongTai.setNewMsg(ordersVos.size());
//            } else {
//                int count = (int) ordersVos.stream()
//                        .filter(order -> order.getOrderDate().after(dongTai.getDateTime()))
//                        .count();
//                dongTai.setNewMsg(count);
//            }
//            Optional<Date> maxOrderDate = ordersVos.stream()
//                    .map(OrdersVo::getOrderDate)
//                    .max(Comparator.naturalOrder());
//            if (maxOrderDate.isPresent()) {
//                dongTai.setDateTime(maxOrderDate.get());
//            } else {
//                dongTai.setDateTime(new Date());
//            }
//            this.dongTaiMapper.updateById(dongTai);
//            return ordersVos;
//        }
        return ordersVos;
    }

    public Boolean getDongTai(int userId) {
        DongTai dongTai = this.dongTaiMapper.getByUserId(userId);
        if (dongTai != null) {
            dongTai.setNewMsg(0);
            return this.dongTaiMapper.updateById(dongTai)>0;
        }
        return false;
    }

    public DongTai getDongTaiNum(int userId) {
        List<OrdersVo> ordersVos = this.ordersMapper.getAllOrder();
        DongTai dongTai = this.dongTaiMapper.getByUserId(userId);
        if (ordersVos.size()>0) {
            if (dongTai != null) {
                return dongTai;
            } else {
                DongTai dongTai1 = new DongTai();
                dongTai1.setUserId(userId);
                dongTai1.setNewMsg(ordersVos.size());
                this.dongTaiMapper.insert(dongTai1);
                return dongTai1;
            }
        }
        return dongTai;
    }

    public void setDongTaiNum(int userId) {
        DongTai dongTai = this.dongTaiMapper.getByUserId(userId);
        dongTai.setNewMsg(dongTai.getNewMsg() + 1);
        this.dongTaiMapper.updateById(dongTai);
    }

    public List<OrdersVo> getOrdersByDate(String time,String time1) {
        return this.ordersMapper.getOrdersByDate(time,time1);
    }


    public Boolean updateUserAddress(int userId,int id) {
        UserInfo userInfo = this.userMapper.selectById(userId);
        if (userInfo != null) {
            List<Address> addresses = this.addressMapper.getAddressByUserId(userId);
            for (Address address1: addresses) {
                address1.setChecked("false");
            }
            userInfo.setAddressId(id);
            this.userMapper.updateById(userInfo);
            Address address = this.addressMapper.selectById(id);
            address.setChecked("true");
            this.addressMapper.updateById(address);
            return true;
        }
        return false;
    }
}
