package com.yummy.repository;

import com.yummy.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

    User findByUsername(String username);

    User findByEmail(String email);

    User findUserById(Long id);

    List<User> getAllByType(String type);

}
