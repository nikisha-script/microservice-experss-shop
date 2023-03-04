package ru.product.pservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.product.pservice.document.Category;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends MongoRepository<Category, UUID> {

    Optional<Category> findByName(String name);

}
