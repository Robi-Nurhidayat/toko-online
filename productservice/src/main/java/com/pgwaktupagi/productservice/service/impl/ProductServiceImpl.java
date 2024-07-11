package com.pgwaktupagi.productservice.service.impl;

import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.entity.Product;
import com.pgwaktupagi.productservice.exception.ProductAlreadyExistsException;
import com.pgwaktupagi.productservice.exception.ResourceNotFoundException;
import com.pgwaktupagi.productservice.mapper.ProductMapper;
import com.pgwaktupagi.productservice.repository.ProductRepository;
import com.pgwaktupagi.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ProductDTO fetchProduct(String name) {
        Product product = productRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Product", "name", name)
        );

        return ProductMapper.mapToProductDTO(product,new ProductDTO());
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findByName(product.getName());
        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exist in database " + product.getName());
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(ProductDTO productDTO) {

        Product product = productRepository.findById(productDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productDTO.getId())
        );

        ProductMapper.mapToProduct(productDTO, product);
        return productRepository.save(product);


    }

    @Override
    public boolean deleteProduct(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId)
        );

        productRepository.deleteById(product.getId());

        return true;
    }
}
