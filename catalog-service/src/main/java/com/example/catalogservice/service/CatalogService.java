package com.example.catalogservice.service;

import com.example.catalogservice.repository.CatalogEntity;
import java.util.List;
import org.springframework.stereotype.Service;

public interface CatalogService {
  List<CatalogEntity> getAllCatalog();
}
