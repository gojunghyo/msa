package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
  private UserRepository userRepository;
  BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    userDto.setUserId(UUID.randomUUID().toString());
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserEntity userEntity = mapper.map(userDto, UserEntity.class);
    userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword()));

    userRepository.save(userEntity);

    UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

    return returnUserDto;
  }

  @Override
  public UserDto getUserById(String userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if(userEntity == null)
      throw new UsernameNotFoundException("User Not Found");

    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
    List<ResponseOrder> orders = new ArrayList<>();
    userDto.setOrders(orders);

    return userDto;
  }

  @Override
  public Boolean delete(String userId) {
    UserEntity findUser = userRepository.findByUserId(userId);

    if(!findUser.getUserId().equals(userId) || findUser == null) {
      return Boolean.FALSE;
    }
    userRepository.delete(findUser);
    return Boolean.TRUE;
  }

  @Override
  public Iterable<UserEntity> getUserByAll() {
    return userRepository.findAll();
  }

  @Override
  public Boolean updateUser(String userId, UserDto userDto) {
    UserEntity findUser = userRepository.findByUserId(userId);

    log.info("In userId = {}", userId);
    if(findUser == null) return Boolean.FALSE;

    userRepository.updateUserId(userId, userDto.getName(), userDto.getEmail(),
        passwordEncoder.encode(userDto.getPassword()));

    return Boolean.TRUE;
  }

  @Override
  public UserDto getUserDetailsByEmail(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);
    if(userEntity == null)
      throw new UsernameNotFoundException("user not found");

//    ModelMapper mapper = new ModelMapper();
//    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

    return userDto;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email);
    log.info("email = {}", email);
    if(userEntity == null)
      throw new UsernameNotFoundException(email);

    return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
        true, true, true, true, new ArrayList<>());
  }
}
