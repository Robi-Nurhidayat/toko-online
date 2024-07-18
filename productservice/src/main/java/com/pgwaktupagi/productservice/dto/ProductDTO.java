package com.pgwaktupagi.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Schema(
        name = "Products",
        description = "Schema to hold Products information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @Schema(
            description = "Id", example = "auto"
    )
    private String id;

    @Schema(
            description = "Product name", example = "Fried Chicken"
    )
    private String name;

    @Schema(
            description = "Product Price", example = "8000"
    )
    private Integer price;

    @Schema(
            description = "Product Stock", example = "10"
    )
    private Integer stock;

    @Schema(
            description = "Product Description", example = "Fried Chicken is ,,,,"
    )
    private String description;

    private String category;

    private String image;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
