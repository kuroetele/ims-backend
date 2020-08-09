package com.kuro.ims.repository;

import com.kuro.ims.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByCustomer_Id(Long customerId);
}
