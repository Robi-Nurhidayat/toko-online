package com.pgwaktupagi.productservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Setter @Getter
public class Product {

    @Id
    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private String description;
}
