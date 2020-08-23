package com.kuro.ims.repository;

import com.kuro.ims.entity.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findByCategory_Id(Long categoryId);

    @Query("select p from Product p where p.availableQuantity < p.minQuantity order by p.id DESC")
    List<Product> findProductsWithLowStock(Pageable pageable);

    @Query(value = "select p.name, p.serial_number as serialNumber, sum(op.quantity) count\n" +
        "from orders\n" +
        "         join order_product op on orders.id = op.order_id\n" +
        "         join product p on op.product_id = p.id\n" +
        "where month(orders.created_at) = month(current_date)\n" +
        "group by p.name, p.serial_number\n" +
        "order by count desc\n", nativeQuery = true)
    List<Map<String, Long>> findTopSellingProducts(Pageable pageable);

    Optional<Product> findBySerialNumber(String serialNumber);
}
