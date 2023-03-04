package ru.product.pservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.product.pservice.document.Category;
import ru.product.pservice.dto.RequestCategoryDto;
import ru.product.pservice.mapper.RequestCategoryDtoMapper;
import ru.product.pservice.repository.mongodb.CategoryRepository;
import ru.product.pservice.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final RequestCategoryDtoMapper mapper;

    @Override
    public void save(RequestCategoryDto requestCategoryDto) {
        Optional<Category> categoryExist = categoryRepository.findByName(requestCategoryDto.getName());
        if (categoryExist.isPresent()) {
            throw new RuntimeException(); // TODO
        }
        Category category = mapper.requestCategoryDtoToCategory(requestCategoryDto);
        category.setId(UUID.randomUUID().toString());
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
