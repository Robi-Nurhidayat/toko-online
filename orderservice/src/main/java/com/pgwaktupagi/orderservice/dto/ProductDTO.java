package com.pgwaktupagi.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotEmpty(message = "Product name cannot be null")
    @Schema(description = "Product name", example = "Fried Chicken")
    private String name;

    @NotEmpty(message = "Product price cannot be null")
    @Min(value = 0, message = "Product price must be greater than or equal to 0")
    @Schema(description = "Product Price", example = "8000")
    private Integer price;

    @NotEmpty(message = "Product stock cannot be null")
    @Min(value = 0, message = "Product stock must be greater than or equal to 0")
    @Schema(description = "Product Stock", example = "10")
    private Integer stock;

    @NotNull(message = "Product description cannot be null")
    @Schema(
            description = "Product Description", example = "Fried Chicken is ,,,,"
    )
    private String description;

    @NotEmpty(message = "Product category cannot be null")
    @Schema(
            description = "Product Category", example = "Makanan | Minuman"
    )
    private String category;

    @Schema(
            description = "Product image with extension jpg,jpeg,png", example = "food.jpg"
    )
    private String image;

    @Schema(
            hidden = true,
            description = "Product image url", example = "http://localhost:8000/api/image/123e213213_avatar.png"
    )
    private String imageUrl;

    @Schema(hidden = true)
    private LocalDateTime createdAt;

    @Schema(hidden = true)
    private LocalDateTime updatedAt;
}
