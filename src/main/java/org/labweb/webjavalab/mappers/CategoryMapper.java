package org.labweb.webjavalab.mappers;

import org.labweb.webjavalab.domain.adapters.usecases.entities.Category;
import org.labweb.webjavalab.dtos.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toCategory(CategoryDTO categoryDTO);
}
