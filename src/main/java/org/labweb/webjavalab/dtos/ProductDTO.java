package org.labweb.webjavalab.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.labweb.webjavalab.domain.adapters.usecases.entities.product.Category;
import org.labweb.webjavalab.exceptions.validation.CosmoValid;

import java.util.UUID;

public record ProductDTO(
        UUID id,
        
        @NotBlank
        @Size(min = 3, max = 50, message = "Product name must be between 3 and 50")
        @CosmoValid
        String name,

        @Min(1)
        double price,

        Category category
) {
}
