package com.example.orderservice.repository;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {
  //직렬화 넣는 목적은 가지고있는 객체를 다른 데이터로 전송 하거나 db 에 넣기위함
  //객체를 byte 형식으로 만들기위함
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120, unique = true)
  private String orderId;

  @Column(nullable = false)
  private String productId;

  @Column(nullable = false)
  private String qty;

  @Column(nullable = false)
  private String unitPrice;

  @Column(nullable = false)
  private String totalPrice;

  @Column(nullable = false, length = 120, unique = false)
  private String userId;

  @CreationTimestamp
  private LocalDateTime createdAt;


}
