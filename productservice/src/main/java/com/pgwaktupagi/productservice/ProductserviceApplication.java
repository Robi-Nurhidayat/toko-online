package com.pgwaktupagi.productservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@OpenAPIDefinition(
		info = @Info(
				title = "Products microservice REST API Documentation",
				description = "Toko Online Products microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Robi Nurhidayat",
						email = "robivanpersie290899@gmail.com",
						url = "https://github.com/Robi-Nurhidayat"
				),
				license = @License(
						name = "Robi Nurhidayat",
						url = "https://github.com/Robi-Nurhidayat"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Toko Online Products microservice REST API Documentation",
				url = "https://github.com/Robi-Nurhidayat/toko-online"
		)
)
public class ProductserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}

}
