package ru.product.pservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.product.pservice.document.Category;
import ru.product.pservice.dto.RequestCategoryDto;
import ru.product.pservice.service.CategoryService;
import ru.product.pservice.service.ProductService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AdminController {


    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/add-category")
    public String getAddCategory(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category";
    }

    @GetMapping("/add-product")
    public String getAddProduct() {
        return "product";
    }

    @PostMapping("/create-category")
    public String addCategory(@RequestParam(name = "name-category") String name,
                              @RequestParam(name = "file") MultipartFile photo) throws IOException {
        categoryService.save(RequestCategoryDto.builder()
                        .name(name)
                        .img(photo.getBytes())
                .build());
        return "redirect:/add-category";
    }

    @GetMapping("/photo/{name}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable("name") String name) {
        Category category = categoryService.findByName(name).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not found");
        });
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(category.getImg().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(category.getImg()));
    }

}
