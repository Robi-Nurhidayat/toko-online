package com.pgwaktupagi.productservice.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ResponseProductWithList {

    @Schema(description = "Status code", example = "200")
    private String statusCode;

    @Schema(description = "Response message", example = "Sukses get all data")
    private String message;

    @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
    private List<ProductDTO> data;
}
