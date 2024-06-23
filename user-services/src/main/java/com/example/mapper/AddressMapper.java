package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {



    List<Address> getAddressByUserId(@Param("userId") int userId);

    Address deleteAddressById(@Param("id") int id);
}
