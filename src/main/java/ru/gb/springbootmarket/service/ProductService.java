package ru.gb.springbootmarket.service;

import org.springframework.stereotype.Service;
import ru.gb.springbootmarket.model.Product;
import ru.gb.springbootmarket.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductRequest {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getAllByImageUrl(String imageUrl) { return productRepository.findAllByImageUrl(imageUrl);}

    public void save(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByTitle(String title) {return productRepository.findByTitle(title);}

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
