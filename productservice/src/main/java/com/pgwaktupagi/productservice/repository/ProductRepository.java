package com.pgwaktupagi.productservice.repository;

import com.pgwaktupagi.productservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

//    Product findById(String id);

    Optional<Product> findByName(String name);
}
