package com.example.userservice.service;


import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  UserDto createUser(UserDto userDto);
  UserDto getUserById(String userId);
  Boolean delete(String userId);
  Iterable<UserEntity> getUserByAll();

  Boolean updateUser(String userId, UserDto userDto);

  UserDto getUserDetailsByEmail(String userName);
}
