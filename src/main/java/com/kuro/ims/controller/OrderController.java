package com.kuro.ims.controller;

import com.kuro.ims.config.security.CustomUserDetails;
import com.kuro.ims.dto.EntityId;
import com.kuro.ims.dto.OrderDto;
import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.Order;
import com.kuro.ims.entity.User;
import com.kuro.ims.service.OrderService;
import com.kuro.ims.type.PaymentType;
import com.kuro.ims.type.Role;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
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
    public Response<EntityId> createOrder(@RequestBody @Valid OrderDto orderDto)
    {
        return Response.<EntityId>builder()
            .data(orderService.createOrder(orderDto))
            .build();
    }


    @GetMapping
    public Response<List<Order>> getOrders(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        Authentication authentication)
    {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        return Response.<List<Order>>builder()
            .data(orderService.getOrders(startDate, endDate, user.getRole() == Role.SALES_PERSON ? user.getId() : null))
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
    public Response<?> getYearlySum(Authentication authentication)
    {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        return Response.<List<?>>builder()
            .data(orderService.getYearlySalesSum(user.getRole() == Role.SALES_PERSON ? user.getId() : null))
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
