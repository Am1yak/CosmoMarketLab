package org.labweb.webjavalab.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CategoryDTO(
        UUID id,

        @NotBlank
        @Size(min = 3, max = 50, message = "Category name must be between 3 and 50")
        String name
) {
}
