package org.labweb.webjavalab.domain.adapters.usecases;

import lombok.RequiredArgsConstructor;
import org.labweb.webjavalab.domain.adapters.usecases.entities.product.Category;
import org.labweb.webjavalab.dtos.CategoryDTO;
import org.labweb.webjavalab.exceptions.CategoryNotFoundException;
import org.labweb.webjavalab.mappers.CategoryMapper;
import org.labweb.webjavalab.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDTO> getAllCategories() {
        HashMap<UUID, Category> categories = categoryRepository.getCategories();
        ArrayList<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : categories.values()) {
            categoryDTOs.add(categoryMapper.toDTO(category));
        }
        return categoryDTOs;
    }

    public Category getCategoryById(UUID id) {
        if (!categoryRepository.existsCategory(id)){
            throw new CategoryNotFoundException("No Category found with id " + id);
        }

        return categoryRepository.getCategory(id);
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toCategory(categoryDTO);
        category.setId(UUID.randomUUID());
        return categoryRepository.save(category);
    }

    public Category updateCategory(UUID id, CategoryDTO categoryDTO) {
        if (!categoryRepository.existsCategory(id)){
            throw new CategoryNotFoundException("No Category found with id " + id);
        }

        Category category = categoryMapper.toCategory(categoryDTO);
        category.setId(id);
        return categoryRepository.updateCategory(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.removeCategory(id);
    }
}
