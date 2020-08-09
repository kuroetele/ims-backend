package com.kuro.ims.controller;

import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.Category;
import com.kuro.ims.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CategoryController
{
    private CategoryService categoryService;


    @RequestMapping("/api/categories")
    public Response<List<Category>> getCategories()
    {
        return Response.<List<Category>>builder()
            .data(categoryService.getCategories())
            .build();
    }


    @RequestMapping("/api/categories/{id}")
    public Response<Category> getCategory(@PathVariable Long id)
    {
        return Response.<Category>builder()
            .data(categoryService.getCategory(id))
            .build();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/categories")
    public void createCategory(@RequestBody Category category)
    {
        categoryService.createCategory(category);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/api/categories/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody Category category)
    {
        categoryService.updateCategory(id, category);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/api/categories/{id}")
    public void disableCategory(@PathVariable Long id)
    {
        categoryService.disableCategory(id);
    }


}
