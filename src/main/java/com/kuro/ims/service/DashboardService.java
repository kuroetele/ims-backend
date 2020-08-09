package com.kuro.ims.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DashboardService
{
    private final CategoryService categoryService;

    private final CustomerService customerService;

    private final OrderService orderService;

    private final ProductService productService;

    private final UserService userService;


    public Map<String, Long> totalCounts()
    {
        Map<String, Future<Long>> kpis = new HashMap<>();

        Map<String, Long> result = new HashMap<>();

        kpis.put("customers", CompletableFuture.supplyAsync(customerService::getCustomerCount));
        kpis.put("products", CompletableFuture.supplyAsync(productService::getProductCount));
        kpis.put("orders", CompletableFuture.supplyAsync(orderService::getOrderCount));
        kpis.put("users", CompletableFuture.supplyAsync(userService::getUserCount));
        kpis.put("categories", CompletableFuture.supplyAsync(categoryService::getCategoryCount));

        kpis.forEach((k, v) -> {
            try
            {
                result.put(k, v.get());
            }
            catch (InterruptedException | ExecutionException e)
            {
                log.error(null, e);
            }
        });

        return result;
    }


    public Map<String, BigDecimal> getTotalSums()
    {
        Map<String, Future<BigDecimal>> kpis = new HashMap<>();

        Map<String, BigDecimal> result = new HashMap<>();

        kpis.put("current-month-total-sales", CompletableFuture.supplyAsync(orderService::getMonthlyOrderSum));

        kpis.forEach((k, v) -> {
            try
            {
                result.put(k, v.get());
            }
            catch (InterruptedException | ExecutionException e)
            {
                log.error(null, e);
            }
        });

        return result;
    }
}
