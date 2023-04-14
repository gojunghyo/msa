package com.example.orderservice.controller;


import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.repository.OrderEntity;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order-service")
public class OrderController {
  private Environment env;
  private OrderService orderService;
  @Autowired
  public OrderController(Environment env, OrderService orderService) {
    this.env = env;
    this.orderService = orderService;
  }

  @GetMapping("/health_check")
  public String status(HttpServletRequest httpServletRequest){
    log.info("PORT = {}", httpServletRequest.getServerPort());
    return String.format("It`s Order-Service Port = %s", env.getProperty("local.server.port"));
  }

  @PostMapping("/{userId}/orders")
  public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
      @RequestBody RequestOrder orderDetails){
    log.info("userId = {}", userId);
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(
        MatchingStrategies.STRICT);

    OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
    orderDto.setUserId(userId);
    OrderDto createdOrder = orderService.createOrder(orderDto);

    ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<List<ResponseOrder>> get0rders (@PathVariable("userId") String userId){
    List<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

    List<ResponseOrder> resultList = new ArrayList<>();
    orderList.forEach((entity) -> {
      resultList.add(new ModelMapper().map(entity, ResponseOrder.class));
    });

    return ResponseEntity.status(HttpStatus.OK).body(resultList);
  }




}
