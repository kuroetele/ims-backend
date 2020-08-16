package com.kuro.ims.service;

import com.kuro.ims.entity.Category;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.CategoryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
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
        category.setId(id);
        categoryRepository.save(category);
    }


    public void deleteCategory(Long id)
    {
        Category category = getCategory(id);
        category.setDeleted(true);
        categoryRepository.save(category);
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
