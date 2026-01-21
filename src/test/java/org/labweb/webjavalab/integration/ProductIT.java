package org.labweb.webjavalab.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.labweb.webjavalab.AbstractIt;
import org.labweb.webjavalab.dtos.ProductDTO;
import org.labweb.webjavalab.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ProductIT extends AbstractIt {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    public void testProductLifecycle() throws Exception {
        ProductDTO product = new ProductDTO(null, "Cosmicshake", 100.00, null);

        String response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Cosmicshake"))
                .andReturn().getResponse().getContentAsString();

        ProductDTO responseProduct = objectMapper.readValue(response, ProductDTO.class);
        UUID id = responseProduct.id();

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].name").value("Cosmicshake"));

        mockMvc.perform(get("/api/v1/products/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        ProductDTO newProduct = new ProductDTO(id, "Cosmicdonut", 100.00, null);

        mockMvc.perform(patch("/api/v1/products/{id}", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Cosmicdonut"));

        mockMvc.perform(delete("/api/v1/products/{id}", id.toString()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private static Stream<ProductDTO> getInvalidProducts(){
        List<ProductDTO> products = new ArrayList<>();
        products.add(new ProductDTO(null, "", 100.00, null));
        products.add(new ProductDTO(null, "Co", 100.00, null));
        products.add(new ProductDTO(null, "Shake", 100.00, null));
        products.add(new ProductDTO(null, "Cosmicshake", 0, null));
        return products.stream();
    }

    @ParameterizedTest
    @MethodSource("getInvalidProducts")
    public void testNotValidProducts(ProductDTO invalidProduct) throws Exception {
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }
}
