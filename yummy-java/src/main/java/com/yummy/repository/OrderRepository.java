package com.yummy.repository;

import com.yummy.modal.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders getOrdersByCode(String code);

    Orders getOrdersById(Long id);

    List<Orders> getAllByUserid(Long userid);

    List<Orders> getAllByShopid(Long shopid);

    List<Orders> getAllByIdAfter(Long id);

}
