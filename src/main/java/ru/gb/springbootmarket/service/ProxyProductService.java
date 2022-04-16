package ru.gb.springbootmarket.service;

import org.springframework.stereotype.Service;
import ru.gb.springbootmarket.model.Product;

import java.util.List;

@Service
public class ProxyProductService implements ProductRequest{
    private final ProductService productService;
    private List<Product> productList;
    private Long updateTime = 0L;

    public ProxyProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> getAll() {
        if (updateTime == 0L) productList = productService.getAll();
        if (productList.isEmpty() || (System.currentTimeMillis() - updateTime) > 600000L) {
            productList = productService.getAll();
            updateTime = System.currentTimeMillis();
        }
        return productList;
    }
}
