package com.pgwaktupagi.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgwaktupagi.userservice.constant.UserConstants;
import com.pgwaktupagi.userservice.dto.UserDTO;
import com.pgwaktupagi.userservice.dto.UserResponse;
import com.pgwaktupagi.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/userservice/uploads/";

    @GetMapping
    public ResponseEntity<UserResponse> getAllUser() {
        List<UserDTO> allUsers = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(UserConstants.STATUS_200,UserConstants.MESSAGE_200,allUsers));
    }

    @GetMapping("/fetch")
    public ResponseEntity<UserResponse> fetchUser(@RequestParam("email") String email) {

        UserDTO user = userService.findUser(email);

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(UserConstants.STATUS_200,UserConstants.MESSAGE_200,user));


    }

    @PostMapping( consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponse> createProduct( @RequestPart("user") String userJson,
                                                         @RequestParam("image") MultipartFile image) throws IOException {

//        ObjectMapper objectMapper = new ObjectMapper();
//        UserDTO userDTO;
//
//        try {
//            userDTO = objectMapper.readValue(userJson, UserDTO.class);
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().body(new UserResponse("400", "Invalid JSON format", null));
//        }

//        ResponseEntity<ResponseProduct> responseProductCustomeError = CustomeError.ValidationErrorFieldCustome(productDTOJSON);
//        if (responseProductCustomeError != null) {
//            return responseProductCustomeError;
//        }
        var userDTO = userService.save(userJson, image);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/image/")
                .path(userDTO.getProfile())
                .toUriString();

        userDTO.setProfileUrl(fileDownloadUri);
        userDTO.setProfile(image.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse(UserConstants.STATUS_201, UserConstants.MESSAGE_201, userDTO));
    }

    @PutMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponse> updateUser(@RequestPart("user") String userJson,
                                                   @RequestParam("image") MultipartFile image) throws IOException {
        UserDTO userDTO = userService.update(userJson, image);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/image/")
                .path(userDTO.getProfile())
                .toUriString();

        userDTO.setProfileUrl(fileDownloadUri);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse(UserConstants.STATUS_200, UserConstants.MESSAGE_200, userDTO));
    }




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


    @DeleteMapping
    public ResponseEntity<UserResponse> delete(@RequestParam("userId") String userId){

        boolean isDelete = userService.deleteUser(Long.parseLong(userId));

        if (isDelete) {
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(UserConstants.STATUS_200,UserConstants.MESSAGE_200,null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(UserConstants.STATUS_417,UserConstants.MESSAGE_417_DELETE,null));


    }


}
