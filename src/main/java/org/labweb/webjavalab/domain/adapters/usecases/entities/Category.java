package org.labweb.webjavalab.domain.adapters.usecases.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Category {
    private UUID id;
    private String name;
}
