package com.kuro.ims.service;

import com.kuro.ims.dto.OrderDto;
import com.kuro.ims.entity.Customer;
import com.kuro.ims.entity.Order;
import com.kuro.ims.entity.OrderProduct;
import com.kuro.ims.entity.Product;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.exception.OrderCreationException;
import com.kuro.ims.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final ProductService productService;

    @Transactional
    public void createOrder(OrderDto orderDto)
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

        BigDecimal netAmount = orderProducts.stream()
            .map(OrderProduct::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderProducts(orderProducts);
        order.setNetAmount(netAmount);
        order.setGrossAmount(netAmount.add(netAmount.multiply(BigDecimal.valueOf(5D))));
        order.setPaymentType(orderDto.getPaymentType());
        order.setDiscountPercentage(orderDto.getDiscountPercentage());
        orderRepository.save(order);
    }


    public List<Order> getOrders()
    {
        return orderRepository.findAll();
    }


    public Order getOrder(Long id)
    {
        return orderRepository.findById(id).orElseThrow(() -> new ImsClientException("order not found"));
    }


    public List<Order> getCustomerOrders(Long customerId)
    {
        return orderRepository.findByCustomer_Id(customerId);
    }


    public Long getOrderCount()
    {
        return this.orderRepository.count();
    }


    public BigDecimal getMonthlyOrderSum()
    {
        LocalDateTime firstDayOfTheMonth = LocalDateTime.now()
            .toLocalDate()
            .atTime(LocalTime.MIN)
            .with(TemporalAdjusters.firstDayOfMonth());

        LocalDateTime lastDayOfTheMonth = LocalDateTime.now()
            .toLocalDate()
            .atTime(LocalTime.MAX)
            .with(TemporalAdjusters.lastDayOfMonth());

        return this.orderRepository.totalSumBetween(firstDayOfTheMonth, lastDayOfTheMonth).orElse(BigDecimal.ZERO);
    }
}
