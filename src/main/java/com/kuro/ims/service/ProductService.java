package com.kuro.ims.service;

import com.kuro.ims.entity.Category;
import com.kuro.ims.entity.Product;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        productRepository.save(product);
    }


    public void updateProduct(Long productId, Product product)
    {
        Category category = categoryService.getCategory(product.getCategoryId());
        product.setCategory(category);
        product.setId(productId);
        productRepository.save(product);
    }


    @Transactional
    public void vendProduct(Product product, Integer count)
    {
        product.setAvailableQuantity(product.getAvailableQuantity() - count);
        productRepository.save(product);
    }


    public void deleteProduct(Long id)
    {
        Product product = this.getProduct(id);
        product.setDeleted(true);
        productRepository.save(product);
    }


    public Long getProductCount()
    {
        return this.productRepository.count();
    }


    public List<Product> getFirst5ProductsRunningLow(int size)
    {
        return productRepository.findProductsWithLowStock(PageRequest.of(0, size));
    }
}
