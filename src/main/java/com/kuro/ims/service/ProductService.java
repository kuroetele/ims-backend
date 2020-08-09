package com.kuro.ims.service;

import com.kuro.ims.entity.Category;
import com.kuro.ims.entity.Product;
import com.kuro.ims.repository.ProductRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
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


    public Product getProduct(Long id)
    {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("product not found"));
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


    public void disableProduct(Long id)
    {
        Product product = this.getProduct(id);
        product.setEnabled(false);
        productRepository.save(product);
    }
}
