package com.pgwaktupagi.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;

    private String name;

    private Integer price;

    private Integer stock;

    private String description;

    private String image;

    private String imageUrl;
}
