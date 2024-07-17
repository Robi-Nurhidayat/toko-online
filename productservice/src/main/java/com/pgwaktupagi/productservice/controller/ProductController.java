package com.pgwaktupagi.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgwaktupagi.productservice.constant.ProductConstants;
import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.dto.ResponseProduct;
import com.pgwaktupagi.productservice.entity.Product;
import com.pgwaktupagi.productservice.mapper.ProductMapper;
import com.pgwaktupagi.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
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

    private static final String UPLOAD_DIR = "uploads/";


    @GetMapping("/product")
    public ResponseEntity<ResponseProduct> getAllProduct() {
        List<ProductDTO> productDTOS = productService.getAllProduct();


        ResponseProduct responseProduct = new ResponseProduct();
        responseProduct.setStatusCode(HttpStatus.OK.toString());
        responseProduct.setMessage("Sukses get all data");
        responseProduct.setData(productDTOS);
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

        log.info("Received product JSON: {}", productJson);
        log.info("Received image: {}", image.getOriginalFilename());

        var productDTO = productService.createProduct(productJson, image);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(productDTO.getImage())
                .toUriString();

        productDTO.setImageUrl(fileDownloadUri);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseProduct(ProductConstants.STATUS_201, ProductConstants.MESSAGE_201, productDTO));
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

    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(UPLOAD_DIR).resolve(filename);
            log.info("Trying to read file from path: {}", file.toString());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                log.info("File content type: {}", contentType);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                log.error("File not found or not readable: {}", filename);
                throw new RuntimeException("Could not read the file!");
            }
        } catch (IOException e) {
            log.error("Error reading file: {}", filename, e);
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }




}
