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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private static final String UPLOAD_DIR = "uploads/";
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

        return ProductMapper.mapToProductDTO(product, new ProductDTO());
    }

    @Override
    @Transactional
    public Product createProduct(Product product, MultipartFile image) throws IOException {
        Optional<Product> optionalProduct = productRepository.findByName(product.getName());
        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exist in database " + product.getName());
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        product.setImages(fileName);

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
