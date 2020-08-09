package com.kuro.ims.service;

import com.kuro.ims.dto.OrderDto;
import com.kuro.ims.entity.Customer;
import com.kuro.ims.entity.Order;
import com.kuro.ims.entity.OrderProduct;
import com.kuro.ims.entity.Product;
import com.kuro.ims.exception.OrderCreationException;
import com.kuro.ims.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final ProductService productService;

    @Value("${order.vatPercentage: 5}")
    private Double vatPercentage;


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
                throw new OrderCreationException("The requested quantity is not available for " + product.getName());
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(quantity);
            orderProduct.setUnitPrice(product.getPrice());
            orderProduct.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            orderProduct.setOrder(order);
            product.setAvailableQuantity(product.getAvailableQuantity() - 1);
            orderProducts.add(orderProduct);
        });

        BigDecimal netAmount = orderProducts.stream()
            .map(OrderProduct::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderProducts(orderProducts);
        order.setNetAmount(netAmount);
        order.setGrossAmount(netAmount.add(netAmount.multiply(BigDecimal.valueOf(vatPercentage))));
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
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("order not found"));
    }


    public List<Order> getCustomerOrders(Long customerId)
    {
        return orderRepository.findByCustomer_Id(customerId);
    }
}
