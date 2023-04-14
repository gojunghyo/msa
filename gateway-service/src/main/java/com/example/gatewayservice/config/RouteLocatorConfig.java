//package com.example.gatewayservice.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RouteLocatorConfig {
//
//  @Bean
//  public RouteLocator routeLocator(RouteLocatorBuilder builder){
//    return builder.routes()
//        .route("first-service", p -> p.path("/first-service/**")
//            .uri("http://localhost:8081"))
//        .route("second-service", p -> p.path("/second-service/**")
//            .uri("http://localhost:8082"))
//        .build();
//  }
//}
