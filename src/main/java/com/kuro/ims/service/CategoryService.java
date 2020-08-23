package com.kuro.ims.service;

import com.kuro.ims.entity.Category;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.CategoryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService
{

    private final CategoryRepository categoryRepository;


    public Category getCategory(Long id)
    {
        return categoryRepository.findById(id).orElseThrow(() -> new ImsClientException("category not found"));
    }


    public void createCategory(Category category)
    {
        categoryRepository.save(category);
    }


    public void updateCategory(Long id, Category category)
    {
        Category existingCategory = getCategory(id);
        BeanUtils.copyProperties(category, existingCategory);
        existingCategory.setId(id);
        categoryRepository.save(existingCategory);
    }

    public List<Category> getCategories()
    {
        return categoryRepository.findAll();
    }


    public Long getCategoryCount()
    {
        return categoryRepository.count();
    }
}
