package com.example.firstservice;

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
@RequestMapping("/first-service")
public class FirstController {
  Environment env;

  @Autowired
  public FirstController(Environment env) {
    this.env = env;
  }

  @GetMapping("/welcome")
  public String welcome(){
    return "Welcome to the first service.";
  }

  @GetMapping("/message")
  public String message(@RequestHeader("first-request") String header){
    log.info("first-request = {}", header);
    return "Hello world in First service.";
  }

  @GetMapping("/check")
  public String check(HttpServletRequest request) {
    log.info("ServerPort = {}", request.getServerPort());

    return String.format("Hi, there. This is a message from First Service on Port = %s.",
        env.getProperty("local.server.port"));
  }
}
