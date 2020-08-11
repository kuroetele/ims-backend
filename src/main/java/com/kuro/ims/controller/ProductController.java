package com.kuro.ims.controller;

import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.Product;
import com.kuro.ims.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController
{
    private final ProductService productService;


    @GetMapping
    public Response<List<Product>> getProducts()
    {
        return Response.<List<Product>>builder()
            .data(productService.getProducts())
            .build();
    }


    @GetMapping("/low-stock")
    public Response<List<Product>> getLowStockProducts(@RequestParam(defaultValue = "5") int size)
    {
        return Response.<List<Product>>builder()
            .data(productService.getFirst5ProductsRunningLow(size))
            .build();
    }


    @GetMapping("/categories/{categoryId}")
    public Response<List<Product>> getProductsByCategory(@PathVariable Long categoryId)
    {
        return Response.<List<Product>>builder()
            .data(productService.getProductsByCategory(categoryId))
            .build();
    }


    @RequestMapping("/{id}")
    public Response<Product> getProduct(@PathVariable Long id)
    {
        return Response.<Product>builder()
            .data(productService.getProduct(id))
            .build();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public void createProduct(@RequestBody Product product)
    {
        productService.createProduct(product);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product product)
    {
        productService.updateProduct(id, product);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void disableProduct(@PathVariable Long id)
    {
        productService.disableProduct(id);
    }


}
