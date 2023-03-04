package ru.product.pservice.mapper;

import org.mapstruct.Mapper;
import ru.product.pservice.document.Category;
import ru.product.pservice.dto.RequestCategoryDto;

@Mapper
public interface RequestCategoryDtoMapper {

    Category requestCategoryDtoToCategory(RequestCategoryDto requestCategoryDto);

}
