//package com.learning.UrlShortner.controller;
//
//import com.learning.UrlShortner.service.UrlService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.URI;
//
//@RestController
//@Tag(name = "URL Redirect", description = "Redirect using a short code")
//public class RedirectController {
//    private final UrlService urlService;
//
//    public RedirectController(UrlService urlService) {
//        this.urlService = urlService;
//    }
//
//    @GetMapping("/{shortCode:[a-zA-Z0-9-]{1,63}}")
//    @Operation(summary = "Redirect to the original URL")
//    public ResponseEntity<Void> redirect(
//            @PathVariable String shortCode) {
//
//        String originalUrl = urlService.getOriginalUrl(shortCode);
//
//        return ResponseEntity
//                .status(HttpStatus.FOUND)
//                .location(URI.create(originalUrl))
//                .build();
//    }
//}


package com.learning.UrlShortner.controller;

import com.learning.UrlShortner.service.UrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Tag(
        name = "URL Redirect",
        description = "Redirect using a short code"
)
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }


    @GetMapping("/{shortCode:[a-zA-Z0-9-]{1,63}}")
    @Operation(summary = "Redirect to original URL")
    public ResponseEntity<Void> redirect(

            @PathVariable String shortCode) {

        String originalUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

}