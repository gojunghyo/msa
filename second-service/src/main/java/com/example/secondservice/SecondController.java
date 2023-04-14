package com.example.secondservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/second-service")
public class SecondController {
  @Autowired
  Environment env;
  public SecondController(Environment env){
    this.env = env;
  }


  @GetMapping("/welcome")
  public String welcome(){
    return "Welcome to the second service.";
  }

  @GetMapping("/message")
  public String message(@RequestHeader("second-request") String header){
    log.info("second-request = {}", header);
    return "Hello world in Second service.";
  }

  @GetMapping("/check")
  public String check(HttpServletRequest request) {
    log.info("request server port = {}", request.getServerPort());
    return String.format("Hi, there. This is a message from Second Service server port = %s"
    , env.getProperty("local.server.port"));
  }
}
