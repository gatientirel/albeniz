package com.theodo.albeniz.repositories;

import com.theodo.albeniz.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
    // @Query("SELECT user FROM UserEntity user WHERE user.username LIKE
    // %:username%")
    UserEntity findByUsername(@Param("username") String username);

}
