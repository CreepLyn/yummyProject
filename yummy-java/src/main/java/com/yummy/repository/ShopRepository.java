package com.yummy.repository;

import com.yummy.modal.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShopRepository extends JpaRepository<Shop, Long > {

    Shop findShopById(Long id);

    Shop findByShopname(String shopname);

    Shop findByUserid(Long userid);

    List<Shop> getAllByType(String type);

    List<Shop> getAllByState(String state);

    List<Shop> findAllByShopnameContaining(String shopname);

}
