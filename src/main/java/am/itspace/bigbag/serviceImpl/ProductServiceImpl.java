package am.itspace.bigbag.serviceImpl;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.Category;
import am.itspace.bigbag.model.Product;
import am.itspace.bigbag.model.ProductType;
import am.itspace.bigbag.repository.ProductRepository;
import am.itspace.bigbag.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> allProducts() {
        List<Product> all = productRepository.findAll();
        return all;
    }

    @Override
    public Product getById(int id) throws ResourceNotFoundException {
        Product one = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with" + id + " does not exist"));
        return one;
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }


    @Override
    public List<Product> findByCategoryAndType(Category category, ProductType type) {
        List<Product> allByCategoryAndType = productRepository.findAllByCategoryAndType(category, type);
        return allByCategoryAndType;
    }

    @Override
    public Product findByProductCode(String code) {
        Product byProductCode = productRepository.findByProductCode(code);
        return byProductCode;
    }

    @Override
    public List<Product> findAllByNameStartingWith(String name) {
        return productRepository.findAllByNameStartingWith(name);
    }
}
