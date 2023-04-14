package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.repository.OrderEntity;
import java.util.List;

public interface OrderService {
  OrderDto createOrder(OrderDto orderDetails);
  OrderDto getOrderByOrderId(String orderId);
  List<OrderEntity> getOrdersByUserId(String userId);
}
