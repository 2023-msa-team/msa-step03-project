package com.example.catalogservice.catalog;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogservice._core.utils.ApiUtils;
import com.example.catalogservice.catalog.dto.CatalogResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CatalogController {
    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/")
    public ResponseEntity<?> healthCheck() {
        String responseBody = String.format("catalog-service on Port %s", env.getProperty("local.server.port"));
        return ResponseEntity.ok(ApiUtils.success(responseBody));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<?> findAll(){
        List<CatalogResponse.ListDTO> respDTOs = catalogService.상품목록();
        return ResponseEntity.ok(ApiUtils.success(respDTOs));
    }
}
