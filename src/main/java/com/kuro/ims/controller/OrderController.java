package com.kuro.ims.controller;

import com.kuro.ims.dto.OrderDto;
import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.Order;
import com.kuro.ims.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController
{
    private final OrderService orderService;


    @PostMapping
    public void createOrder(@RequestBody OrderDto orderDto)
    {
        orderService.createOrder(orderDto);
    }


    @GetMapping
    public Response<List<Order>> getOrders()
    {
        return Response.<List<Order>>builder()
            .data(orderService.getOrders())
            .build();
    }


    @GetMapping("{id}")
    public Response<Order> getOrder(@PathVariable Long id)
    {
        return Response.<Order>builder()
            .data(orderService.getOrder(id))
            .build();
    }


    @GetMapping("/customers/{customerId}")
    public Response<List<Order>> getCustomerOrders(@PathVariable Long customerId)
    {
        return Response.<List<Order>>builder()
            .data(orderService.getCustomerOrders(customerId))
            .build();
    }


}
