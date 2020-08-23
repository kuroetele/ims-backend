package com.kuro.ims.service;

import com.kuro.ims.entity.Category;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.CategoryRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest
{
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;


    @Test
    void getCategoryByIdWhenCategoryExist()
    {
        //given
        Category category = new Category();
        category.setName("Computers");
        category.setDescription("computers and electronics");
        category.setId(1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        //when
        Category categoryFound = categoryService.getCategory(1L);

        //then
        assertThat(categoryFound).isEqualToComparingFieldByField(category);
        verify(categoryRepository).findById(eq(1L));
    }


    @Test
    void getCategoryByIdWhenCategoryDoesNotExist()
    {
        //when /then
        assertThatThrownBy(() -> categoryService.getCategory(1L))
            .isInstanceOf(ImsClientException.class)
            .hasMessage("category not found");
    }


    @Test
    void createCategory()
    {
        //given
        Category category = new Category();
        category.setName("Computers");
        category.setDescription("computers and electronics");
        category.setId(1L);

        //when
        categoryService.createCategory(category);

        //then
        verify(categoryRepository).save(categoryArgumentCaptor.capture());

        assertThat(categoryArgumentCaptor.getValue()).isEqualToComparingFieldByField(category);
    }


    @Test
    void updateCategoryWhenCategoryDoesNotExist()
    {
        //given
        Category category = new Category();
        category.setName("Computers");
        category.setDescription("computers and electronics");

        //when /then
        assertThatThrownBy(() -> categoryService.updateCategory(1L, category))
            .isInstanceOf(ImsClientException.class)
            .hasMessage("category not found");
    }


    @Test
    void updateCategoryWhenCategoryExist()
    {
        //given
        Category existingCategory = new Category();
        existingCategory.setName("Computers");
        existingCategory.setDescription("computers and electronics");
        existingCategory.setId(1L);

        Category update = new Category();
        BeanUtils.copyProperties(existingCategory, update);
        update.setName("New Computers");
        update.setId(null);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(existingCategory));

        //when
        categoryService.updateCategory(1L, update);

        //then
        verify(categoryRepository).save(categoryArgumentCaptor.capture());

        assertThat(categoryArgumentCaptor.getValue()).isEqualToIgnoringGivenFields(existingCategory, "name");
        assertThat(categoryArgumentCaptor.getValue().getName()).isEqualTo("New Computers");
    }


    @Test
    void getCategories()
    {
        //given
        Category category = new Category();
        category.setName("Computers");
        category.setDescription("computers and electronics");
        category.setId(1L);

        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));

        //when
        List<Category> categories = categoryService.getCategories();

        //then
        assertThat(categories).hasSize(1);
        assertThat(categories).containsExactly(category);
        verify(categoryRepository).findAll();
    }


    @Test
    void getCategoryCount()
    {
        //given
        when(categoryRepository.count()).thenReturn(5L);

        //when
        Long count = categoryService.getCategoryCount();

        //then
        assertThat(count).isEqualTo(5L);
        verify(categoryRepository).count();
    }
}
