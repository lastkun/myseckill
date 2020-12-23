package com.ghk.seckill.dao;

import com.ghk.seckill.domian.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CustomerDao {

    @Select("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = #{id}")
    Customer getCustomerById(@Param("id")String id);

    @Update("UPDATE CUSTOMER SET PASSWORD = #{password} WHERE CUSTOMER_ID = #{customerId}")
    void updateById(Customer customer);
}
