package com.yummy.repository;

import com.yummy.modal.Orderinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderinfoRepository extends JpaRepository<Orderinfo,Long> {

    List<Orderinfo> findAllByOrderid(Long orderid);
}
