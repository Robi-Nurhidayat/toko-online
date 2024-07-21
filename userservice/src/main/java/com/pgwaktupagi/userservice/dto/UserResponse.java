package com.pgwaktupagi.userservice.dto;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Schema(
//        name = "Response",
//        description = "Schema to hold Response information"
//)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

//    @Schema(
//            description = "Response Status Code", example = "200"
//    )
    private String statusCode;
//    @Schema(
//            description = "Response Message", example = "Created product successfully"
//    )
    private String message;

//    @Schema(
//            description = "Response Data contains data from database"
//    )
    private Object data;
}
