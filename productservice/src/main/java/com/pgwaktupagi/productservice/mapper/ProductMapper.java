package com.pgwaktupagi.productservice.mapper;


import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.entity.Product;

public class ProductMapper {


    public static ProductDTO mapToProductDTO(Product product, ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setDescription(product.getDescription());
        return productDTO;
    }

    public static Product mapToProduct(ProductDTO productDTO, Product product) {
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        return product;
    }
}
