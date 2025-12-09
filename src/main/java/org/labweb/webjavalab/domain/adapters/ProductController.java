package org.labweb.webjavalab.domain.adapters;

import lombok.RequiredArgsConstructor;
import org.labweb.webjavalab.domain.adapters.usecases.CategoryService;
import org.labweb.webjavalab.domain.adapters.usecases.ProductService;
import org.labweb.webjavalab.domain.adapters.usecases.entities.Product;
import org.labweb.webjavalab.dtos.ProductDTO;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public List<ProductDTO> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id,
                                                 @Valid @RequestBody ProductDTO product) {
        return ResponseEntity.ok().body(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO product) {
        return ResponseEntity.status(201).body(productService.createProduct(product));
    }

    @PatchMapping("/{productId}/{categoryId}")
    public ResponseEntity<Product> addCategory(@PathVariable UUID productId, @PathVariable UUID categoryId) {
        return ResponseEntity.ok(productService.addCategoryToProduct(
                productService.getProductById(productId),
                categoryService.getCategoryById(categoryId)
            )
        );
    }
}
