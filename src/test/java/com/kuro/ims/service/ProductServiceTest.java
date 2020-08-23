package com.kuro.ims.service;

import com.kuro.ims.entity.Category;
import com.kuro.ims.entity.Product;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest
{

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;


    @Test
    void getProducts()
    {
        //given
        Product product = getTestProduct();

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        //when
        List<Product> products = productService.getProducts();

        //then
        assertThat(products).hasSize(1);
        assertThat(products).containsExactly(product);
        verify(productRepository).findAll();
    }


    @Test
    void getProductsByCategory()
    {
    }


    @Test
    void getProductByIdWhenProductExist()
    {
        //given
        Product product = getTestProduct();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        //when
        Product foundProduct = productService.getProduct(1L);

        //then
        assertThat(foundProduct).isEqualToComparingFieldByField(product);
        verify(productRepository).findById(eq(1L));
    }


    @Test
    void getProductByIdWhenProductDoesNotExist()
    {
        //given /then
        assertThatThrownBy(() -> productService.getProduct(1L))
            .isInstanceOf(ImsClientException.class)
            .hasMessage("product not found");
    }


    @Test
    void createProduct()
    {
        //given
        Product product = getTestProduct();

        //when
        productService.createProduct(product);

        //then
        verify(productRepository).save(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue()).isEqualTo(product);
    }


    @Test
    void updateProductWhenProductExist()
    {
        //given
        Product existingProduct = getTestProduct();

        Product update = new Product();
        BeanUtils.copyProperties(existingProduct, update);
        update.setName("New Product");
        update.setCategoryId(1L);
        update.setId(null);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        when(categoryService.getCategory(anyLong())).thenReturn(new Category());

        //when
        productService.updateProduct(1L, update);

        //then
        verify(productRepository).save(productArgumentCaptor.capture());
        verify(categoryService).getCategory(eq(1L));
        verify(productRepository).findById(eq(1L));
        assertThat(productArgumentCaptor.getValue()).isEqualToIgnoringGivenFields(existingProduct, "name");
        assertThat(productArgumentCaptor.getValue().getName()).isEqualTo("New Product");
    }


    @Test
    void updateProductWhenProductDoesNotExist()
    {
        //given
        Product product = getTestProduct();

        //when /then
        assertThatThrownBy(() -> productService.updateProduct(1L, product))
            .isInstanceOf(ImsClientException.class)
            .hasMessage("product not found");
    }


    @Test
    void getProductCount()
    {
        //given
        when(productRepository.count()).thenReturn(5L);

        //when
        Long count = productService.getProductCount();

        //then
        assertThat(count).isEqualTo(5L);
        verify(productRepository).count();
    }


    @Test
    void getFirstXProductsRunningLow()
    {
        //given
        Product product = getTestProduct();

        when(productRepository.findProductsWithLowStock(any())).thenReturn(Collections.singletonList(product));

        //when
        List<Product> products = productService.getFirstXProductsRunningLow(5);

        //then
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        assertThat(products).hasSize(1);
        assertThat(products).containsExactly(product);
        verify(productRepository).findProductsWithLowStock(pageableArgumentCaptor.capture());
        assertThat(pageableArgumentCaptor.getValue().getPageSize()).isEqualTo(5);
    }


    @Test
    void getFirstXTopSellingProducts()
    {
        //given
        Map<String, Long> items = Collections.singletonMap("HP Pro", 1L);

        when(productRepository.findTopSellingProducts(any())).thenReturn(Collections.singletonList(items));

        //when
        List<Map<String, Long>> products = productService.getFirstXTopSellingProducts(5);

        //then
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        assertThat(products).hasSize(1);
        assertThat(products).containsExactly(items);
        verify(productRepository).findTopSellingProducts(pageableArgumentCaptor.capture());
        assertThat(pageableArgumentCaptor.getValue().getPageSize()).isEqualTo(5);
    }


    private Product getTestProduct()
    {
        Product product = new Product();
        product.setSerialNumber("12345");
        product.setName("Mac Book");
        product.setAvailableQuantity(2L);
        product.setCostPrice(BigDecimal.ONE);
        product.setPrice(BigDecimal.TEN);

        Category category = new Category();
        category.setName("test");
        category.setDescription("test description");

        product.setCategory(category);
        return product;
    }
}
