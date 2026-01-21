package org.labweb.webjavalab.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labweb.webjavalab.domain.adapters.usecases.CategoryService;
import org.labweb.webjavalab.domain.adapters.usecases.entities.product.Category;
import org.labweb.webjavalab.dtos.CategoryDTO;
import org.labweb.webjavalab.exceptions.CategoryNotFoundException;
import org.labweb.webjavalab.mappers.CategoryMapper;
import org.labweb.webjavalab.repositories.CategoryRepository;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    @Test
    public void testFindAll() {
        UUID id = UUID.randomUUID();
        Category category = Category.builder().id(id).name("Category").build();
        CategoryDTO categoryDTO = new CategoryDTO(id, "Category");
        HashMap<UUID, Category> categoryHashMap = new HashMap<>();
        categoryHashMap.put(id, category);

        when(categoryRepository.getCategories()).thenReturn(categoryHashMap);
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        List<CategoryDTO> res = categoryService.getAllCategories();

        assertEquals(1, res.size());
        assertEquals("Category", res.getFirst().name());
    }

    @Test
    public void testFindById_Found() {
        UUID id = UUID.randomUUID();
        Category category = Category.builder().id(id).name("Category").build();

        when(categoryRepository.existsCategory(id)).thenReturn(true);
        when(categoryRepository.getCategory(id)).thenReturn(category);

        Category res = categoryService.getCategoryById(id);
        assertEquals(category, res);
    }

    @Test
    public void testFindById_NotFound() {
        UUID id = UUID.randomUUID();

        when(categoryRepository.existsCategory(id)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(id));
    }

    @Test
    public void testCreateCategory() {
        Category category = Category.builder().id(null).name("Category").build();
        CategoryDTO categoryDTO = new CategoryDTO(null, "Category");
        Category savedCategory = Category.builder().id(UUID.randomUUID()).name("Category").build();

        when(categoryMapper.toCategory(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);

        categoryService.createCategory(categoryDTO);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());

        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(category, capturedCategory);
    }

    @Test
    public void testUpdateCategory_Found() {
        UUID id = UUID.randomUUID();
        Category category = Category.builder().id(id).name("Category").build();
        CategoryDTO categoryDTO = new CategoryDTO(null, "Category");

        when(categoryRepository.existsCategory(id)).thenReturn(true);
        when(categoryRepository.updateCategory(category)).thenReturn(category);
        when(categoryMapper.toCategory(categoryDTO)).thenReturn(category);

        categoryService.updateCategory(id, categoryDTO);
        verify(categoryRepository).updateCategory(categoryArgumentCaptor.capture());

        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(category, capturedCategory);
    }

    @Test
    public void testUpdateCategory_NotFound() {
        UUID id = UUID.randomUUID();
        CategoryDTO categoryDTO = new CategoryDTO(null, "Category");

        when(categoryRepository.existsCategory(id)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(id, categoryDTO));
    }
}
