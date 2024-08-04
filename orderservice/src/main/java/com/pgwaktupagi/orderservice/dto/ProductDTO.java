package com.pgwaktupagi.orderservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private String id;

    private String name;

    private Integer price;

    private Integer stock;

    private String description;

    private String category;

    private String image;

    private String imageUrl;

    private Date createdAt;

    private Date updatedAt;
}
