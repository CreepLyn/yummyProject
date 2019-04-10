package com.yummy.repository;

import com.yummy.modal.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address getAddressById(Long id);

    List<Address> getAllByUserid(Long userid);
}
