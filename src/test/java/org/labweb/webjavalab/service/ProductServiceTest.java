package org.labweb.webjavalab.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labweb.webjavalab.domain.adapters.usecases.CategoryService;
import org.labweb.webjavalab.domain.adapters.usecases.ProductService;
import org.labweb.webjavalab.domain.adapters.usecases.entities.Category;
import org.labweb.webjavalab.domain.adapters.usecases.entities.Product;
import org.labweb.webjavalab.dtos.ProductDTO;
import org.labweb.webjavalab.exceptions.ProductNotFoundException;
import org.labweb.webjavalab.mappers.ProductMapper;
import org.labweb.webjavalab.repositories.ProductRepository;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Test
    public void testFindAll() {
        UUID id = UUID.randomUUID();
        Product product = getValidProduct(id);
        ProductDTO productDTO = new ProductDTO(id, "Product Name", 100.00, null);
        HashMap<UUID, Product> products = new HashMap<>();
        products.put(id, product);

        when(productRepository.getProducts()).thenReturn(products);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        List<ProductDTO> productDTOS = productService.getAllProducts();

        assertEquals(1, productDTOS.size());
        assertEquals(productDTO, productDTOS.getFirst());
    }

    @Test
    public void testFindById_Found() {
        UUID id = UUID.randomUUID();
        Product product = getValidProduct(id);

        when(productRepository.existsProduct(id)).thenReturn(true);
        when(productRepository.getProduct(id)).thenReturn(product);

        Product res = productService.getProductById(id);
        assertEquals(product, res);
    }

    @Test
    public void testFindById_NotFound() {
        UUID id = UUID.randomUUID();

        when(productRepository.existsProduct(id)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
    }

    @Test
    public void testCreateProduct() {
        UUID id = UUID.randomUUID();
        Product product = getValidProduct(id);
        ProductDTO productDTO = new ProductDTO(id, "Product Name", 100.00, null);

        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        Product res = productService.createProduct(productDTO);
        verify(productRepository).save(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(product, capturedProduct);
    }

    @Test
    public void testUpdateProduct_Found() {
        UUID id = UUID.randomUUID();
        Product product = getValidProduct(id);
        ProductDTO productDTO = new ProductDTO(id, "Product Name", 100.00, null);

        when(productRepository.existsProduct(id)).thenReturn(true);
        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(productRepository.updateProduct(product)).thenReturn(product);

        productService.updateProduct(id, productDTO);
        verify(productRepository).updateProduct(productCaptor.capture());
        Product capturedProduct = productCaptor.getValue();

        assertEquals(product, capturedProduct);
    }

    @Test
    public void testUpdateProduct_NotFound() {
        UUID id = UUID.randomUUID();
        ProductDTO productDTO = new ProductDTO(id, "Product Name", 100.00, null);
        when(productRepository.existsProduct(id)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(id, productDTO));
    }

    @Test
    public void testAddCategory_Found() {
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        Product product = getValidProduct(productId);
        Category category = Category.builder().id(categoryId).name("Category Name").build();

        when(productRepository.existsProduct(productId)).thenReturn(true);
        when(productRepository.getProduct(productId)).thenReturn(product);
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        product.setCategory(category);
        productService.addCategoryToProduct(productId, categoryId);
        verify(productRepository).save(productCaptor.capture());
        Product capturedProduct = productCaptor.getValue();
        assertEquals(product, capturedProduct);
    }

    @Test
    public void testAddCategory_NotFound() {
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        when(productRepository.existsProduct(productId)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.addCategoryToProduct(productId, categoryId));
    }

    private Product getValidProduct(UUID id) {
        return Product.builder().id(id)
                .name("Product Name")
                .price(100.00)
                .build();
    }
}
