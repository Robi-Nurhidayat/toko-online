package com.pgwaktupagi.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgwaktupagi.productservice.constant.ProductConstants;
import com.pgwaktupagi.productservice.constant.ProductDocumentation;
import com.pgwaktupagi.productservice.dto.ErrorResponseDto;
import com.pgwaktupagi.productservice.dto.ProductDTO;
import com.pgwaktupagi.productservice.dto.ProductInfo;
import com.pgwaktupagi.productservice.dto.ResponseProduct;
import com.pgwaktupagi.productservice.service.IProductService;
import com.pgwaktupagi.productservice.utils.CustomeError;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Tag(
        name = "CRUD REST APIs for Products in Toko Online",
        description = "CRUD REST APIs in Toko Online to CREATE, UPDATE, FETCH AND DELETE product details"
)

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProductController {

    private final IProductService productService;
    private final ProductInfo productInfo;
    @Autowired
    private ObjectMapper objectMapper;



    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/productservice/uploads/";



    @Operation(summary = "Get All Product REST API", description = "REST API to fetch all product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseProduct.class),
                            examples = @ExampleObject(value = ProductDocumentation.GET_ALL_DATA_DOC))
            ),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(value = ProductDocumentation.ERROR_DATA_DOC)
                    )
            )
    })
    @GetMapping("/product")
    public ResponseEntity<ResponseProduct> getAllProduct(@RequestHeader("tokoonline-correlation-id") String correlationId) {

        System.out.println("ini correlation id : " + correlationId);
        List<ProductDTO> productDTOS = productService.getAllProduct();


        ResponseProduct responseProduct = new ResponseProduct();
        responseProduct.setStatusCode(ProductConstants.STATUS_200);
        responseProduct.setMessage("Sukses get all data");
        responseProduct.setData(productDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(responseProduct);

    }


    @Operation(
            summary = "Fetch Product REST API",
            description = "REST API to fetch Product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = ProductDocumentation.FETCH_PRODUCT_DOC)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(value = ProductDocumentation.ERROR_DATA_DOC)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<ResponseProduct> fetchDetailProduct(@RequestParam(value = "name") String name) throws IOException {
        ProductDTO productDTO = productService.fetchProduct(name);
        log.info(name);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(productDTO.getImage())
                .toUriString();
        productDTO.setImage(productDTO.getImage().substring(productDTO.getImage().indexOf("_")+1));
        productDTO.setImageUrl(fileDownloadUri);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseProduct(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200, productDTO));
    }

    @GetMapping("/product/find-by-id")
    public ResponseEntity<ResponseProduct> findbyId(@RequestParam("productId") String productId) throws IOException {
        ProductDTO productDTO = productService.findById(productId);
        log.info(productId);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(productDTO.getImage())
                .toUriString();
        productDTO.setImage(productDTO.getImage().substring(productDTO.getImage().indexOf("_")+1));
        productDTO.setImageUrl(fileDownloadUri);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseProduct(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200, productDTO));
    }

    @Operation(
            summary = "Create New Product REST API",
            description = "REST API to create new Product Toko Online"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = ProductDocumentation.CREATE_PRODUCT_DOC)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping(value = "/product", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseProduct> createProduct(@Valid  @RequestPart("product") String productJson,
                                                         @RequestParam("image") MultipartFile image) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTOJSON;

        try {
            productDTOJSON = objectMapper.readValue(productJson, ProductDTO.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ResponseProduct("400", "Invalid JSON format", null));
        }

        ResponseEntity<ResponseProduct> responseProductCustomeError = CustomeError.ValidationErrorFieldCustome(productDTOJSON);
        if (responseProductCustomeError != null) {
            return responseProductCustomeError;
        }
        var productDTO = productService.createProduct(productJson, image);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(productDTO.getImage())
                .toUriString();

        productDTO.setImageUrl(fileDownloadUri);
        productDTO.setImage(image.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseProduct(ProductConstants.STATUS_201, ProductConstants.MESSAGE_201, productDTO));
    }


    @Operation(
            summary = "Delete Product REST API",
            description = "REST API to delete Product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseProduct.class),
                            examples = @ExampleObject(value = ProductDocumentation.DELETE_PRODUCT_DOC)

                    )
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(value = ProductDocumentation.ERROR_DATA_DOC)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(value = ProductDocumentation.ERROR_DATA_DOC)
                    )
            )
    }
    )
    @DeleteMapping("/product")
    public ResponseEntity<ResponseProduct> deleteProduct(@RequestParam String productId) {
        boolean isDeleted = productService.deleteProduct(productId);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseProduct(HttpStatus.OK.toString(), "Success delete product", ""));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseProduct(ProductConstants.STATUS_417, ProductConstants.MESSAGE_417_DELETE, ""));
        }
    }


    @Operation(
            summary = "Update Product Details REST API",
            description = "REST API to update Product Based on ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = ProductDocumentation.UPDATE_PRODUCT_DOC)
                    )

            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(value = ProductDocumentation.ERROR_DATA_DOC)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(value = ProductDocumentation.ERROR_DATA_DOC)
                    )
            )
    }
    )
    @PutMapping(value = "/product", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseProduct> updateProduct(@Valid @RequestPart("product") String productJson,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTOJSON;

        try {
            productDTOJSON = objectMapper.readValue(productJson, ProductDTO.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ResponseProduct("400", "Invalid JSON format", null));
        }

        ResponseEntity<ResponseProduct> responseProductCustomeError = CustomeError.ValidationErrorFieldCustome(productDTOJSON);
        if (responseProductCustomeError != null) {
            return responseProductCustomeError;
        }

        var productDTO = productService.updateProduct(productJson, image);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(productDTO.getImage())
                .toUriString();

        productDTO.setImageUrl(fileDownloadUri);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseProduct(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200, productDTO));
    }

    @Hidden
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


    @GetMapping("/products/contact-info")
    public ResponseEntity<ProductInfo> getProductInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(productInfo);
    }



}
