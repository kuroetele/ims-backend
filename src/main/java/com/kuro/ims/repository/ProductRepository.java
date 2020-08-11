package com.kuro.ims.repository;

import com.kuro.ims.entity.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findByCategory_Id(Long categoryId);

    @Query("select p from Product p where p.availableQuantity < p.minQuantity order by p.id DESC")
    List<Product> findProductsWithLowStock(Pageable pageable);
}
