package com.kuro.ims.repository;

import com.kuro.ims.entity.Order;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>
{
    List<Order> findByCustomer_Id(Long customerId);

    @Query("select sum(o.grossAmount) as amount, year(o.createdAt) as year from Order o group by year")
    List<Map<BigDecimal, String>> findYearlySalesSum();

    @Query("select sum(o.grossAmount) as amount, year(o.createdAt) as year from Order o  where o.createdBy.id =:userId group by year")
    List<Map<BigDecimal, String>> findYearlySalesSumForUser(Long userId);
}
