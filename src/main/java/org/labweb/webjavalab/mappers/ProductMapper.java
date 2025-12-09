package org.labweb.webjavalab.mappers;

import org.labweb.webjavalab.domain.adapters.usecases.entities.Product;
import org.labweb.webjavalab.dtos.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toProduct(ProductDTO productDTO);
}
