package com.pgwaktupagi.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.entity.Product;
import com.pgwaktupagi.productservice.exception.ProductAlreadyExistsException;
import com.pgwaktupagi.productservice.exception.ResourceNotFoundException;
import com.pgwaktupagi.productservice.mapper.ProductMapper;
import com.pgwaktupagi.productservice.repository.ProductRepository;
import com.pgwaktupagi.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {

    private static final String UPLOAD_DIR = "uploads/";
    private final ProductRepository productRepository;


    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> products = new ArrayList<>();
        try {
            products = productRepository.findAll();
        } catch (Exception e) {
            log.error("Error retrieving products", e);
            throw e; // Re-throw or handle accordingly
        }

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (var product : products) {
            ProductDTO productDTO = new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getImages(),
                    null,
                    product.getCreatedAt(),
                    product.getUpdatedAt()
            );

            // Generate image URL
            try {
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/image/")
                        .path(product.getImages())
                        .toUriString();
                productDTO.setImageUrl(fileDownloadUri);
            } catch (Exception e) {
                log.error("Error generating image URL for product: " + product.getName(), e);
                productDTO.setImageUrl(null); // Set to null or handle accordingly
            }

            productDTOS.add(productDTO);
        }

        return productDTOS;
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
    public ProductDTO createProduct(String productJson, MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

        Optional<Product> optionalProduct = productRepository.findByName(productDTO.getName());
        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exist in database " + productDTO.getName());
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        log.info("Saving file to path: {}", path.toString());
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setImages(fileName);

        product = productRepository.save(product);

        productDTO.setImage(product.getImages());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());

        return productDTO;
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
