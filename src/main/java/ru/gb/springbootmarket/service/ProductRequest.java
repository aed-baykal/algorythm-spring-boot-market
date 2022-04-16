package ru.gb.springbootmarket.service;

import ru.gb.springbootmarket.model.Product;

import java.util.List;

public interface ProductRequest {
    List<Product> getAll();
}
