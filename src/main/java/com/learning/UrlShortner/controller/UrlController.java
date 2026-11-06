//package com.learning.UrlShortner.controller;
//
//import com.learning.UrlShortner.dto.CreateUrlRequest;
//import com.learning.UrlShortner.dto.UpdateUrlRequest;
//import com.learning.UrlShortner.dto.UpdateUrlStatusRequest;
//import com.learning.UrlShortner.dto.UrlResponse;
//import com.learning.UrlShortner.service.UrlService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import org.springframework.security.core.Authentication;
//
//import java.net.URI;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/urls")
//@Tag(name="URL Management",description = "Manage shortened URLs")
//public class UrlController {
//    @Autowired
//    public UrlService serv;
//
//    public String getBaseUrl(){
//        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
//    }
//
////    @PostMapping("/create")
////    @Operation(summary = "create a short url")
////    public ResponseEntity<UrlResponse> CreateUrl(@Valid @RequestBody CreateUrlRequest request){
////
////        UrlResponse response = serv.createShortUrl(request, getBaseUrl());
////        return ResponseEntity.created(URI.create(response.getShortUrl())).body(response);
////    }
//
//
//    @PostMapping("/create")
//    public ResponseEntity<UrlResponse> createUrl(
//            @Valid @RequestBody CreateUrlRequest request,
//            Authentication authentication) {
//
//        UrlResponse response = serv.createShortUrl(
//                request,
//                getBaseUrl(),
//                authentication.getName());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(response);
//    }
//
//    @GetMapping("/getAll")
//    @Operation(summary = "Get All urls present in db")
//    public ResponseEntity<List<UrlResponse>> getAll(){
//            return ResponseEntity.ok(serv.getAllUrls(getBaseUrl()));
//    }
//
//    @GetMapping("/get/{shortcode}")
//    @Operation(summary = "Get URL details using a short code")
//    public ResponseEntity<UrlResponse> getbyshort(@PathVariable String shortcode)
//    {
//        return ResponseEntity.ok(serv.getByShortCode(shortcode,getBaseUrl()));
//    }
//
//    @DeleteMapping("/delete/{shortCode}")
//    public ResponseEntity<String> deleteUrl(@PathVariable String shortCode){
//
//        serv.deleteByShortCode(shortCode);
//        return ResponseEntity.ok().body("deleted");
//
//    }
//
//    @PatchMapping("/{shortCode}/status")
//    @Operation(summary = "Enable or disable a short URL")
//    public ResponseEntity<String> updateStatus(
//            @PathVariable String shortCode,
//            @Valid @RequestBody UpdateUrlStatusRequest request) {
//
//        serv.updateStatus(shortCode, request);
//
//        return ResponseEntity.ok("Updated");
//    }
//
//
//    @PutMapping("/update/{shortCode}")
//    @Operation(summary = "Update URL settings without changing the short code")
//    public ResponseEntity<UrlResponse> updateUrlSettings(
//            @PathVariable String shortCode,
//            @Valid @RequestBody UpdateUrlRequest request) {
//
//        UrlResponse response = serv.updateUrlSettings(
//                shortCode,
//                request,
//                getBaseUrl()
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//
//}







package com.learning.UrlShortner.controller;

import com.learning.UrlShortner.dto.CreateUrlRequest;
import com.learning.UrlShortner.dto.UpdateUrlRequest;
import com.learning.UrlShortner.dto.UpdateUrlStatusRequest;
import com.learning.UrlShortner.dto.UrlResponse;
import com.learning.UrlShortner.service.UrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
@Tag(
        name = "URL Management",
        description = "Manage authenticated user's URLs"
)
@SecurityRequirement(name = "Bearer Authentication")
public class UrlController {

    private final UrlService serv;

    public UrlController(UrlService serv) {
        this.serv = serv;
    }


    // Get application base URL
    private String getBaseUrl() {

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();
    }


    // CREATE SHORT URL
    @PostMapping("/create")
    @Operation(summary = "Create a short URL")
    public ResponseEntity<UrlResponse> createUrl(

            @Valid @RequestBody CreateUrlRequest request,

            Authentication authentication) {

        String email = authentication.getName();


        UrlResponse response = serv.createShortUrl(
                request,
                getBaseUrl(),
                email
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // GET ALL URLS OF LOGGED-IN USER
    @GetMapping("/getAll")
    @Operation(summary = "Get all URLs of authenticated user")
    public ResponseEntity<List<UrlResponse>> getAll(

            Authentication authentication) {

        String email = authentication.getName();

        List<UrlResponse> urls = serv.getAllUrls(
                getBaseUrl(),
                email
        );

        return ResponseEntity.ok(urls);
    }


    // GET URL USING SHORT CODE
    @GetMapping("/get/{shortCode}")
    @Operation(summary = "Get URL details using short code")
    public ResponseEntity<UrlResponse> getByShort(

            @PathVariable String shortCode,

            Authentication authentication) {

        String email = authentication.getName();

        UrlResponse response = serv.getByShortCode(
                shortCode,
                getBaseUrl(),
                email
        );

        return ResponseEntity.ok(response);
    }


    // DELETE URL
    @DeleteMapping("/delete/{shortCode}")
    @Operation(summary = "Delete an owned short URL")
    public ResponseEntity<Void> deleteUrl(

            @PathVariable String shortCode,

            Authentication authentication) {

        String email = authentication.getName();

        serv.deleteByShortCode(shortCode, email);

        return ResponseEntity.noContent().build();
    }


    // ENABLE / DISABLE URL
    @PatchMapping("/{shortCode}/status")
    @Operation(summary = "Enable or disable an owned short URL")
    public ResponseEntity<String> updateStatus(

            @PathVariable String shortCode,

            @Valid @RequestBody UpdateUrlStatusRequest request,

            Authentication authentication) {

        String email = authentication.getName();

        serv.updateStatus(
                shortCode,
                request,
                email
        );

        return ResponseEntity.ok("URL status updated successfully");
    }


    // UPDATE URL SETTINGS
    @PutMapping("/update/{shortCode}")
    @Operation(summary = "Update URL settings without changing short code")
    public ResponseEntity<UrlResponse> updateUrlSettings(

            @PathVariable String shortCode,

            @Valid @RequestBody UpdateUrlRequest request,

            Authentication authentication) {

        String email = authentication.getName();

        UrlResponse response = serv.updateUrlSettings(
                shortCode,
                request,
                getBaseUrl(),
                email
        );

        return ResponseEntity.ok(response);
    }

}