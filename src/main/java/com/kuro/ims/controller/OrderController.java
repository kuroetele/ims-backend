package com.kuro.ims.controller;

import com.kuro.ims.dto.OrderDto;
import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.Order;
import com.kuro.ims.service.OrderService;
import com.kuro.ims.type.PaymentType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Response<List<Order>> getOrders(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        return Response.<List<Order>>builder()
            .data(orderService.getOrders(startDate, endDate))
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


    @GetMapping("/yearly-sum")
    public Response<?> getYearlySum()
    {
        return Response.<List<?>>builder()
            .data(orderService.getYearlySalesSum())
            .build();
    }


    @GetMapping("/payment-types")
    public Response<?> getPaymentTypes()
    {
        return Response.<PaymentType[]>builder()
            .data(PaymentType.values())
            .build();
    }


}
