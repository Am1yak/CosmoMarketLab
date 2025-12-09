package org.labweb.webjavalab.domain.adapters.usecases.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Product {
    private UUID id;
    private String name;
    private double price;
    private Category category;
}
