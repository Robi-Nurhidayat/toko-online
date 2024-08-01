package com.pgwaktupagi.productservice.service;

import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {

    List<ProductDTO> getAllProduct();

    ProductDTO fetchProduct(String name) throws IOException;

    ProductDTO createProduct(String productJson, MultipartFile image)  throws IOException;;

    ProductDTO updateProduct(String productJson, MultipartFile image) throws IOException;

    ProductDTO findById(String productId);

    boolean deleteProduct(String productId);

}
