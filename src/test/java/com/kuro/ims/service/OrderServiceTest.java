package com.kuro.ims.service;

import com.kuro.ims.dto.OrderDto;
import com.kuro.ims.entity.Category;
import com.kuro.ims.entity.Order;
import com.kuro.ims.entity.Product;
import com.kuro.ims.entity.Setting;
import com.kuro.ims.repository.OrderRepository;
import com.kuro.ims.type.PaymentType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
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

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Mock
    private MailNotificationService mailNotificationService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;


    @Test
    void createOrder()
    {
        //given
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(1L);
        orderDto.setPaymentType(PaymentType.CASH);
        orderDto.setProductAndQuantity(Collections.singletonMap(1L, 1));

        when(settingService.getStoreSetting()).thenReturn(getTestStoreSetting());
        when(productService.getProduct(any())).thenReturn(getTestProduct());
        when(orderRepository.save(any())).thenReturn(getTestOrder());

        //when
        orderService.createOrder(orderDto);

        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order order = orderArgumentCaptor.getValue();
        assertThat(order.getInvoiceNumber()).isNotNull();
        assertThat(order.getGrossAmount()).isEqualTo(BigDecimal.valueOf(10.5).setScale(3, BigDecimal.ROUND_DOWN));
        assertThat(order.getNetAmount()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(order.getVatPercentage()).isEqualTo(5D);
        assertThat(order.getOrderProducts()).hasSize(1);
        assertThat(order.getOrderProducts().get(0).getProduct()).isEqualToIgnoringGivenFields(getTestProduct(), "availableQuantity");
        assertThat(order.getOrderProducts().get(0).getProduct().getAvailableQuantity()).isEqualTo(getTestProduct().getAvailableQuantity() - 1);
        verify(applicationEventPublisher).publishEvent(order);
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
        //given
        Order order = getTestOrder();

        when(orderRepository.findByCustomer_Id(anyLong()))
            .thenReturn(Collections.singletonList(order));

        //when
        List<Order> orders = orderService.getCustomerOrders(1L);

        //then
        assertThat(orders).hasSize(1);
        assertThat(orders).containsExactly(order);
        verify(orderRepository).findByCustomer_Id(eq(1L));
    }


    @Test
    void getOrderCount()
    {
        //given
        when(orderRepository.count(any(Specification.class))).thenReturn(5L);

        //when
        Long count = orderService.getOrderCount(1L);

        //then
        assertThat(count).isEqualTo(5L);
        verify(orderRepository).count(any(Specification.class));
    }


    @Test
    void getYearlySalesSum()
    {
        //given
        List<Map<BigDecimal, String>> yearlySum = Collections.singletonList(Collections.singletonMap(BigDecimal.TEN, "2020"));

        when(orderRepository.findYearlySalesSum()).thenReturn(yearlySum);

        //when
        List<Map<BigDecimal, String>> sum = orderService.getYearlySalesSum(null);

        //then
        assertThat(sum).containsExactly(Collections.singletonMap(BigDecimal.TEN, "2020"));
        verify(orderRepository).findYearlySalesSum();
    }


    @Test
    void getYearlySalesSumForUser()
    {
        //given
        List<Map<BigDecimal, String>> yearlySum = Collections.singletonList(Collections.singletonMap(BigDecimal.TEN, "2020"));

        when(orderRepository.findYearlySalesSumForUser(any())).thenReturn(yearlySum);

        //when
        List<Map<BigDecimal, String>> sum = orderService.getYearlySalesSum(1L);

        //then
        assertThat(sum).containsExactly(Collections.singletonMap(BigDecimal.TEN, "2020"));
        verify(orderRepository).findYearlySalesSumForUser(eq(1L));
    }


    @Test
    void getMonthlyOrderSumByUser()
    {
        //given

        when(orderRepository.findAll(any(Specification.class), any(Pageable.class)))
            .thenReturn(new PageImpl<>(Collections.singletonList(getTestOrder())));

        //when
        BigDecimal sum = orderService.getMonthlyOrderSum(1L);

        //then
        assertThat(sum).isEqualByComparingTo(BigDecimal.TEN);
        verify(orderRepository).findAll(any(Specification.class), any(Pageable.class));
    }


    @Test
    void getMonthlyOrderSum()
    {
        //given

        when(orderRepository.findAll(any(Specification.class), any(Pageable.class)))
            .thenReturn(new PageImpl<>(Collections.singletonList(getTestOrder())));

        //when
        BigDecimal sum = orderService.getMonthlyOrderSum(null);

        //then
        assertThat(sum).isEqualByComparingTo(BigDecimal.TEN);
        verify(orderRepository).findAll(any(Specification.class), any(Pageable.class));
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


    private Setting getTestStoreSetting()
    {
        Setting setting = new Setting();
        setting.setVatPercentage(5D);
        return setting;
    }


    private Product getTestProduct()
    {
        Product product = new Product();
        product.setSerialNumber("12345");
        product.setName("Mac Book");
        product.setAvailableQuantity(2L);
        product.setCostPrice(BigDecimal.ONE);
        product.setPrice(BigDecimal.TEN);

        Category category = new Category();
        category.setName("test");
        category.setDescription("test description");

        product.setCategory(category);
        return product;
    }
}
