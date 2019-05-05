package com.qaengine.services;

import com.qaengine.database.CategoryRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(ResourceNotFoundException::new);
    }

    public Long getTotalCategories() {
        return categoryRepository.count();
    }
}
