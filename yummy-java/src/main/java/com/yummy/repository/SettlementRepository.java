package com.yummy.repository;

import com.yummy.modal.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    Settlement getSettlementById(Long id);
}
