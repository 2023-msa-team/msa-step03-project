package com.example.catalogservice.catalog;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.catalogservice.catalog.dto.CatalogResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CatalogService {
    
    private final CatalogRepository catalogRepository;

    public List<CatalogResponse.ListDTO> 상품목록(){
        List<Catalog> catalogListPS = catalogRepository.findAll();
        return catalogListPS.stream().map(CatalogResponse.ListDTO::new).collect(Collectors.toList());
    }

}
