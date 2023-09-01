package com.example.orderservice.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice._core.utils.ApiUtils;
import com.example.orderservice.order.dto.OrderRequest;
import com.example.orderservice.order.dto.OrderResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;
    private final Environment env;

    @GetMapping("/")
    public ResponseEntity<?> healthCheck() {
        String responseBody = String.format("order-service on Port %s", env.getProperty("local.server.port"));
        return ResponseEntity.ok(ApiUtils.success(responseBody));
    }

    @PostMapping("/orders")
    public ResponseEntity<?> save(@RequestBody OrderRequest.SaveDTO reqDTO){
        OrderResponse.SaveDTO respDTO = orderService.주문(reqDTO);
        return ResponseEntity.ok(ApiUtils.success(respDTO));
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<?> findByUserId(@PathVariable String userId){
        List<OrderResponse.ListDTO> respDTOs = orderService.주문목록(userId);
        return ResponseEntity.ok(ApiUtils.success(respDTOs));
    }
}
