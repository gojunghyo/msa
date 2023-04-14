package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {
  private UserService userService;
  private Environment env;
  @Autowired
  private Greeting greeting;
  @Autowired //필드주입보다 생성자 주입을 스프링에서 recomanded
  public UserController(Environment env, UserService userService) {
    this.env = env;
    this.userService = userService;
  }

  @GetMapping("/health_check")
  public String status(){
    return String.format("It`s Working in User Service Port = %s", env.getProperty("local.server.port"));
  }

  @GetMapping("/welcome")
  public String welcome(){
    return greeting.getMessage();

//    return env.getProperty("greeting.message");
  }

  @GetMapping("/users")
  public ResponseEntity<List<ResponseUser>> getUsers(){
    Iterable<UserEntity> userList = userService.getUserByAll();
    List<ResponseUser> resultList = new ArrayList<>();

    userList.forEach((v) -> {
      resultList.add(new ModelMapper().map(v, ResponseUser.class));
    });
    return ResponseEntity.status(HttpStatus.OK).body(resultList);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
    UserDto user = userService.getUserById(userId);
    ResponseUser returnUser = new ModelMapper().map(user, ResponseUser.class);
    return ResponseEntity.status(HttpStatus.OK).body(returnUser);
  }

  @PostMapping("/users")
  public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserDto userDto = mapper.map(user, UserDto.class);
    userService.createUser(userDto);
    ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
  }

  @PutMapping("/users/{userId}")
  public ResponseEntity<HttpStatus> updateUser(@PathVariable String userId,@RequestBody RequestUser user) {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);


    UserDto userDto = mapper.map(user, UserDto.class);
    Boolean resultBoolean = userService.updateUser(userId, userDto);

    if(resultBoolean) {
      return ResponseEntity.status(HttpStatus.OK).build();
    }else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") String userId){
    Boolean resultBoolean = userService.delete(userId);

    if(resultBoolean)
      return ResponseEntity.status(HttpStatus.OK).build();
    else
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }


}
