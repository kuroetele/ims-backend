package com.kuro.ims.service;

import com.kuro.ims.dto.EntityId;
import com.kuro.ims.dto.OrderDto;
import com.kuro.ims.entity.Customer;
import com.kuro.ims.entity.Order;
import com.kuro.ims.entity.OrderProduct;
import com.kuro.ims.entity.Product;
import com.kuro.ims.entity.Setting;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.exception.OrderCreationException;
import com.kuro.ims.repository.OrderRepository;
import com.kuro.ims.util.FilterUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final ProductService productService;

    private final SettingService settingService;


    @Transactional
    public EntityId createOrder(OrderDto orderDto)
    {
        Customer customer = customerService.getCustomer(orderDto.getCustomerId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setDiscountPercentage(order.getDiscountPercentage());

        List<OrderProduct> orderProducts = new ArrayList<>();

        orderDto.getProductAndQuantity().forEach((productId, quantity) -> {
            Product product = productService.getProduct(productId);

            if (product.getAvailableQuantity() < quantity)
            {
                throw new OrderCreationException("The requested quantity is not available for product (" + product.getName() + ")");
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(quantity);
            orderProduct.setUnitPrice(product.getPrice());
            orderProduct.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            orderProduct.setOrder(order);

            productService.vendProduct(product, quantity);

            orderProducts.add(orderProduct);
        });

        Setting storeSetting = settingService.getStoreSetting();

        BigDecimal netAmount = orderProducts.stream()
            .map(OrderProduct::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grossAmount = netAmount.add(netAmount.multiply(BigDecimal.valueOf(storeSetting.getVatPercentage() / 100)));
        grossAmount = grossAmount.subtract(grossAmount.multiply(BigDecimal.valueOf(orderDto.getDiscountPercentage() / 100)));

        order.setVatPercentage(storeSetting.getVatPercentage());
        order.setCurrency(storeSetting.getCurrency());

        order.setOrderProducts(orderProducts);
        order.setNetAmount(netAmount);
        order.setGrossAmount(grossAmount);
        order.setPaymentType(orderDto.getPaymentType());
        order.setDiscountPercentage(orderDto.getDiscountPercentage());
        order.setInvoiceNumber(String.valueOf(System.currentTimeMillis()));
        return EntityId.builder()
            .id(orderRepository.save(order).getId())
            .build();
    }


    public List<Order> getOrders(LocalDate startDate, LocalDate endDate, Long userId)
    {
        Specification<Order> specification =
            Specification
                .<Order>where(FilterUtil.buildCreatedAtFilter(startDate, endDate))
                .and(FilterUtil.buildCreatedByFilter(userId));

        return orderRepository.findAll(specification, Pageable.unpaged()).getContent();
    }


    public Order getOrder(Long id)
    {
        return orderRepository.findById(id).orElseThrow(() -> new ImsClientException("order not found"));
    }


    public List<Order> getCustomerOrders(Long customerId)
    {
        return orderRepository.findByCustomer_Id(customerId);
    }


    public Long getOrderCount(Long userId)
    {
        Specification<Order> specification =
            Specification
                .where(FilterUtil.buildCreatedByFilter(userId));

        return this.orderRepository.count(specification);
    }


    public List<Map<BigDecimal, String>> getYearlySalesSum(Long userId)
    {
        if (userId == null)
        {
            return this.orderRepository.findYearlySalesSum();
        }
        else
        {
            return this.orderRepository.findYearlySalesSumForUser(userId);
        }
    }


    public BigDecimal getMonthlyOrderSum(Long userId)
    {
        LocalDate firstDayOfTheMonth = LocalDateTime.now()
            .toLocalDate()
            .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate lastDayOfTheMonth = LocalDateTime.now()
            .toLocalDate()
            .with(TemporalAdjusters.lastDayOfMonth());

        return this.getOrders(firstDayOfTheMonth, lastDayOfTheMonth, userId)
            .stream()
            .map(Order::getGrossAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
