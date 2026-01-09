package org.labweb.webjavalab.domain.adapters;

import lombok.RequiredArgsConstructor;
import org.labweb.webjavalab.domain.adapters.usecases.CategoryService;
import org.labweb.webjavalab.domain.adapters.usecases.entities.Category;
import org.labweb.webjavalab.dtos.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/category")
@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Validated @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(201).body(categoryService.createCategory(categoryDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @Validated @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
