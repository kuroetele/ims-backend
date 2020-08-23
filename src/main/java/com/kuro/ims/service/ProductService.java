package com.kuro.ims.service;

import com.kuro.ims.entity.Category;
import com.kuro.ims.entity.Product;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;

    private final CategoryService categoryService;


    public List<Product> getProducts()
    {
        return productRepository.findAll();
    }


    public List<Product> getProductsByCategory(Long categoryId)
    {
        return productRepository.findByCategory_Id(categoryId);
    }


    public Product getProduct(Long id)
    {
        return productRepository.findById(id).orElseThrow(() -> new ImsClientException("product not found"));
    }


    public void createProduct(Product product)
    {
        Category category = categoryService.getCategory(product.getCategoryId());
        product.setCategory(category);
        checkProductSerialNumber(product.getSerialNumber());
        productRepository.save(product);
    }


    private void checkProductSerialNumber(String serialNumber)
    {
        productRepository.findBySerialNumber(serialNumber)
            .ifPresent(p -> {
                throw new ImsClientException(String.format("%s already has the same serial number %s", p.getName(), serialNumber));
            });
    }


    public void updateProduct(Long productId, Product product)
    {
        Category category = categoryService.getCategory(product.getCategoryId());
        product.setCategory(category);
        product.setId(productId);
        productRepository.save(product);
    }

    public Long getProductCount()
    {
        return this.productRepository.count();
    }


    public List<Product> getFirstXProductsRunningLow(int size)
    {
        return productRepository.findProductsWithLowStock(PageRequest.of(0, size));
    }

    public List<Map<String, Long>> getFirstXTopSellingProducts(int size)
    {
        return productRepository.findTopSellingProducts(PageRequest.of(0, size));
    }
}
