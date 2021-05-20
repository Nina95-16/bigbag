package am.itspace.bigbag.service;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.Product;
import am.itspace.bigbag.model.Category;
import am.itspace.bigbag.model.ProductType;
import am.itspace.bigbag.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    void saveProduct(Product product);

    List<Product> allProducts();

    Product getById(int id) throws ResourceNotFoundException;

    void delete(int id);

    List<Product> findByCategoryAndType(Category category, ProductType type);

    Product findByProductCode(String code) throws ResourceNotFoundException;

    List<Product> findAllByNameStartingWith(String name);
}
