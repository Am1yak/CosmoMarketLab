package org.labweb.webjavalab.domain.adapters.usecases;

import lombok.RequiredArgsConstructor;
import org.labweb.webjavalab.domain.adapters.usecases.entities.product.Category;
import org.labweb.webjavalab.domain.adapters.usecases.entities.product.Product;
import org.labweb.webjavalab.dtos.ProductDTO;
import org.labweb.webjavalab.exceptions.ProductNotFoundException;
import org.labweb.webjavalab.mappers.ProductMapper;
import org.labweb.webjavalab.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    public List<ProductDTO> getAllProducts() {
        HashMap<UUID, Product> products = productRepository.getProducts();
        ArrayList<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products.values()) {
            productDTOs.add(productMapper.toDTO(product));
        }
        return productDTOs;
    }

    public Product getProductById(UUID id) {
        if(!productRepository.existsProduct(id)){
            throw new ProductNotFoundException("No product found with id " + id);
        }

        return productRepository.getProduct(id);
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, ProductDTO productDTO) {
        if(!productRepository.existsProduct(id)){
            throw new ProductNotFoundException("No product found with id " + id);
        }

        Product product = productMapper.toProduct(productDTO);
        product.setId(id);
        return productRepository.updateProduct(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.removeProduct(id);
    }

    public Product addCategoryToProduct(UUID productId, UUID categoryId) {
        if(!productRepository.existsProduct(productId)){
            throw new ProductNotFoundException("No product found with id " + productId);
        }

        Product product = productRepository.getProduct(productId);
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        return productRepository.save(product);
    }
}
