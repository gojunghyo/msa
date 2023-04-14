package com.example.catalogservice.controller;

import com.example.catalogservice.repository.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
  private Environment env;
  private CatalogService catalogService;

  @Autowired
  public CatalogController(Environment env,
      CatalogService catalogService) {
    this.env = env;
    this.catalogService = catalogService;
  }

  @GetMapping("/health_check")
  public String status(){
    return String.format("It`s Working Catalog Service on Port %s", env.getProperty("local.server.port"));
  }

  @GetMapping("/catalogs")
  public ResponseEntity<List<ResponseCatalog>> catalogs(){
    List<CatalogEntity> catalogList = catalogService.getAllCatalog();

    List<ResponseCatalog> resultList = new ArrayList<>();
    catalogList.forEach((entity -> {
      resultList.add(new ModelMapper().map(entity, ResponseCatalog.class));
    }));
    return ResponseEntity.status(HttpStatus.OK).body(resultList);
  }


}
