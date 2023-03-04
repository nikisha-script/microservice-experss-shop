package ru.product.pservice.service;

import ru.product.pservice.document.Category;
import ru.product.pservice.dto.RequestCategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {

    void save(RequestCategoryDto requestCategoryDto);
    List<Category> findAll();

    Optional<Category> findById(UUID id);

    Optional<Category> findByName(String name);

}
