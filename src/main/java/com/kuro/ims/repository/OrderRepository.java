package com.kuro.ims.repository;

import com.kuro.ims.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByCustomer_Id(Long customerId);

    @Query(value = "select sum(o.grossAmount) from Order o where o.createdAt between :startDateTime and :endDateTime")
    Optional<BigDecimal> totalSumBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
