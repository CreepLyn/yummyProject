package com.yummy.repository;

import com.yummy.modal.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Food getFoodById(Long id);

    Food getFoodByMenuidAndName(Long menuid, String name);

    List<Food> findAllByMenuid(Long menuid);

    void deleteAllByMenuid(Long menuid);
}
