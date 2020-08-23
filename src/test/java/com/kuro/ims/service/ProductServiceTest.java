package com.kuro.ims.service;

import com.kuro.ims.entity.Product;
import com.kuro.ims.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest
{

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;


    @Test
    void getProducts()
    {
    }


    @Test
    void getProductsByCategory()
    {
    }


    @Test
    void getProduct()
    {
    }


    @Test
    void createProduct()
    {
    }


    @Test
    void updateProduct()
    {
    }


    @Test
    void deleteProduct()
    {
    }


    @Test
    void getProductCount()
    {
    }


    @Test
    void getFirstXProductsRunningLow()
    {
    }


    @Test
    void getFirstXTopSellingProducts()
    {
    }
}
