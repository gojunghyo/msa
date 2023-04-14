package com.example.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByUserId(String userId);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE UserEntity u SET u.name = :name , u.email = :email, u.encryptedPwd = :password "
      + "WHERE u.userId = :userId")
  void updateUserId(String userId, String name, String email, String password);

  UserEntity findByEmail(String username);
}
