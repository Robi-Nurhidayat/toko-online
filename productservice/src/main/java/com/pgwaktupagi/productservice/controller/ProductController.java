package com.pgwaktupagi.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgwaktupagi.productservice.constant.ProductConstants;
import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.dto.ResponseProduct;
import com.pgwaktupagi.productservice.entity.Product;
import com.pgwaktupagi.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final IProductService productService;

    @GetMapping("/product")
    public ResponseEntity<ResponseProduct> getAllProduct() {
        List<Product> productList = productService.getAllProduct();

        // Update imageUrl in response
        productList.forEach(product -> {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image/")
                    .path(product.getImages())
                    .toUriString();
            product.setImages(fileDownloadUri);
        });

        ResponseProduct responseProduct = new ResponseProduct();
        responseProduct.setStatusCode(HttpStatus.OK.toString());
        responseProduct.setMessage("Sukses get all data");
        responseProduct.setData(productList);
        return ResponseEntity.status(HttpStatus.OK).body(responseProduct);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ResponseProduct> fetchDetailProduct(@RequestParam String name) {
        ProductDTO productDTO = productService.fetchProduct(name);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(productDTO.getImage())
                .toUriString();
        productDTO.setImageUrl(fileDownloadUri);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseProduct(ProductConstants.STATUS_201, ProductConstants.STATUS_200, productDTO));
    }

    @PostMapping(value = "/product", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseProduct> createProduct(@RequestPart("product") String productJson,
                                                         @RequestParam("image") MultipartFile image) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());

        product = productService.createProduct(product, image);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(product.getImages())
                .toUriString();
        productDTO.setImageUrl(fileDownloadUri);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseProduct(ProductConstants.STATUS_201, ProductConstants.MESSAGE_201, productDTO));
    }

    @DeleteMapping("/product")
    public ResponseEntity<ResponseProduct> deleteProduct(@RequestParam String productId) {
        boolean isDeleted = productService.deleteProduct(productId);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseProduct(HttpStatus.OK.toString(), "Success delete product", ""));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseProduct(ProductConstants.STATUS_417, ProductConstants.MESSAGE_417_DELETE, ""));
        }
    }

    @PutMapping("/product")
    public ResponseEntity<ResponseProduct> updateProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.updateProduct(productDTO);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(product.getImages())
                .toUriString();
        productDTO.setImageUrl(fileDownloadUri);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseProduct(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200, productDTO));
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get("uploads/" + filename);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
