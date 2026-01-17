package org.labweb.webjavalab.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.labweb.webjavalab.AbstractIt;
import org.labweb.webjavalab.dtos.CategoryDTO;
import org.labweb.webjavalab.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class CategoryIT extends AbstractIt {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() throws Exception {
        categoryRepository.deleteAll();
    }

    @Test
    public void testCategoryLifeCycle() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(null, "Test Category");

        String response = mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Category"))
                .andReturn().getResponse().getContentAsString();

        CategoryDTO responseDTO = objectMapper.readValue(response, CategoryDTO.class);
        UUID createdCategoryId = responseDTO.id();

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(createdCategoryId.toString()));

        mockMvc.perform(get("/api/v1/category/{id}", createdCategoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCategoryId.toString()));

        CategoryDTO updatedCategoryDTO = new CategoryDTO(createdCategoryId, "Test Category 2");
        mockMvc.perform(patch("/api/v1/category/{id}", createdCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCategoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category 2"));

        mockMvc.perform(delete("/api/v1/category/{id}", createdCategoryId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testNotValidCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(null, "");

        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isBadRequest());

        CategoryDTO categoryDTO1 = new CategoryDTO(null, "ba");

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO1)))
                .andExpect(status().isBadRequest());
    }
}
