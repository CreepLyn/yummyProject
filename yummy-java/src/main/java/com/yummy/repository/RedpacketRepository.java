package com.yummy.repository;

import com.yummy.modal.Redpacket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedpacketRepository extends JpaRepository<Redpacket, Long> {

    List<Redpacket> findAllByUserid(Long userid);

    Redpacket findRedpacketById(Long id);

}
