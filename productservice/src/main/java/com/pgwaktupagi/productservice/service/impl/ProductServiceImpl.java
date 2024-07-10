package com.pgwaktupagi.productservice.service.impl;

import com.pgwaktupagi.productservice.entity.Product;
import com.pgwaktupagi.productservice.exception.ProductAlreadyExistsException;
import com.pgwaktupagi.productservice.repository.ProductRepository;
import com.pgwaktupagi.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findByName(product.getName());
        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exist in database " + product.getName());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String productId) {
//        Product findProductById = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("not found"));
//
//        if (!findProductById) {
//            return new RuntimeException("not found");
//        }
        return null;
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
