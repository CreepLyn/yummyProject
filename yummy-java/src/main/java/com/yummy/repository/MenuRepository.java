package com.yummy.repository;

import com.yummy.modal.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long > {

    Menu getMenuById(Long id);

    Menu getByShopidAndAndName(Long shopid, String name);

    List<Menu> getAllByShopid(Long shopid);

}
