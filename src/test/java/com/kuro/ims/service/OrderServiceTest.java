package com.kuro.ims.service;

import com.kuro.ims.dto.OrderDto;
import com.kuro.ims.entity.Order;
import com.kuro.ims.repository.OrderRepository;
import com.kuro.ims.type.PaymentType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest
{

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @Mock
    private SettingService settingService;


    @Test
    void createOrder()
    {
        //given
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(1L);
        orderDto.setPaymentType(PaymentType.CASH);
        orderDto.setProductAndQuantity(Collections.singletonMap(1L, 1));

        orderService.createOrder(orderDto);
    }


    @Test
    void getOrders()
    {
        //given
        Order order = getTestOrder();

        when(orderRepository.findAll(any(Specification.class), any(Pageable.class)))
            .thenReturn(new PageImpl<>(Collections.singletonList(order)));

        //when
        List<Order> orders = orderService.getOrders(LocalDate.MIN, LocalDate.MAX, 1L);

        //then
        assertThat(orders).hasSize(1);
        assertThat(orders).containsExactly(order);
        verify(orderRepository).findAll(any(Specification.class), any(Pageable.class));
    }


    @Test
    void getOrder()
    {
        //given
        Order order = getTestOrder();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        //when
        Order foundOrder = orderService.getOrder(1L);

        //then
        assertThat(foundOrder).isEqualToComparingFieldByField(order);
        verify(orderRepository).findById(eq(1L));
    }


    @Test
    void getCustomerOrders()
    {
    }


    @Test
    void getOrderCount()
    {
    }


    @Test
    void getYearlySalesSum()
    {
    }


    @Test
    void getMonthlyOrderSum()
    {
    }


    private Order getTestOrder()
    {
        Order order = new Order();
        order.setTotalPaid(BigDecimal.TEN);
        order.setInvoiceNumber("1234567890");
        order.setCurrency("NGN");
        order.setGrossAmount(BigDecimal.TEN);
        return order;
    }
}
