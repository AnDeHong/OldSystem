package com.example.services;

import com.example.entity.Address;
import com.example.entity.AdminImage;
import com.example.entity.UserInfo;
import com.example.mapper.AddressMapper;
import com.example.mapper.UserMapper;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressServices {

    @Resource
    private AddressMapper addressMapper;
    @Resource
    private UserMapper userMapper;

    public Boolean insert(Address address) {
        if (address != null) {
            address.setChecked("false");
            UserInfo userInfo = this.userMapper.getUserByOpenId(UserUtils.getUser().getToken());
            address.userInit(userInfo.getUsername());
            return this.addressMapper.insert(address)>0;
        }
        return false;
    }

    public List<Address> getAddress(int userId) {
        return this.addressMapper.getAddressByUserId(userId);
    }

    public Boolean update(Address address) {
        address.setUpdatedTime(new Date());
        return this.addressMapper.updateById(address)>0;
    }

    public Boolean delete(int id) {
        Address address = this.addressMapper.deleteAddressById(id);
        if (address != null) {
            address.setDeleteMark(Const.YES);
            this.addressMapper.updateById(address);
            return true;
        }
        return false;
    }
}
