package com.example.orderservice.order;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.orderservice.order.dto.OrderRequest;
import com.example.orderservice.order.dto.OrderResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse.SaveDTO 주문(OrderRequest.SaveDTO reqDTO){
        String uuid = UUID.randomUUID().toString();
        Order orderPS = orderRepository.save(reqDTO.toEntity(uuid));
        return new OrderResponse.SaveDTO(orderPS);
    }

    public List<OrderResponse.ListDTO> 주문목록(String userId){
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList.stream().map(OrderResponse.ListDTO::new).collect(Collectors.toList());
    }
}
