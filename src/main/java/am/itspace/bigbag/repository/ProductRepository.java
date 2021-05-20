package am.itspace.bigbag.repository;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.Product;
import am.itspace.bigbag.model.Category;
import am.itspace.bigbag.model.ProductType;
import am.itspace.bigbag.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryAndType(Category category,ProductType type);
    Product findByProductCode(String code);
    List<Product>  findAllByNameStartingWith(String name);
}
