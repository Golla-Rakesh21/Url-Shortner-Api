package com.learning.UrlShortner.service;

import com.learning.UrlShortner.dto.*;
import com.learning.UrlShortner.entity.UrlMapping;
import com.learning.UrlShortner.entity.User;
import com.learning.UrlShortner.exception.ResourceNotFoundException;
import com.learning.UrlShortner.repo.UrlRepository;
import com.learning.UrlShortner.repo.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UrlService {

    private final UrlRepository repo;
    private final UserRepository userRepository;

    public UrlService(
            UrlRepository repo,
            UserRepository userRepository) {

        this.repo = repo;
        this.userRepository = userRepository;
    }

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int CODE_LENGTH = 7;
    private static final int MAX_ATTEMPTS = 10;

    private final SecureRandom secureRandom = new SecureRandom();


    // Generate unique short code
    private String generateUniqueCode() {

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {

            StringBuilder code = new StringBuilder(CODE_LENGTH);

            for (int i = 0; i < CODE_LENGTH; i++) {

                int index = secureRandom.nextInt(CHARACTERS.length());

                code.append(CHARACTERS.charAt(index));
            }

            String candidate = code.toString();

            if (!repo.existsByShortCode(candidate)) {
                return candidate;
            }
        }

        throw new IllegalStateException(
                "Unable to generate unique short code"
        );
    }


    // Convert Entity to DTO
    private UrlResponse toResponse(
            UrlMapping mapping,
            String baseUrl) {

        String shortUrl = baseUrl + "/" + mapping.getShortCode();

        return new UrlResponse(
                mapping.getId(),
                mapping.getOrginalUrl(),
                mapping.getShortCode(),
                shortUrl,
                mapping.getCreatedAt(),
                mapping.getExpiresAt(),
                mapping.isActive()
        );
    }


    // CREATE URL
    public UrlResponse createShortUrl(
            CreateUrlRequest request,
            String baseUrl,
            String authenticatedEmail) {

        User owner = userRepository
                .findByEmailIgnoreCase(authenticatedEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User account not found"
                ));

        String customAlias = request.getCustomAlias();

        String shortCode;

        if (customAlias != null && !customAlias.isBlank()) {

            if (repo.existsByShortCode(customAlias)) {

                throw new IllegalArgumentException(
                        "Custom alias already exists"
                );
            }

            shortCode = customAlias;

        } else {

            shortCode = generateUniqueCode();
        }

        UrlMapping mapping = new UrlMapping();

        mapping.setOrginalUrl(request.getOriginalUrl());
        mapping.setShortCode(shortCode);
        mapping.setActive(true);
        mapping.setExpiresAt(request.getExpiresAt());

        // Associate URL with authenticated user
        mapping.setUser(owner);

        UrlMapping saved = repo.save(mapping);

        return toResponse(saved, baseUrl);
    }


    // GET URL BY SHORT CODE
    public UrlResponse getByShortCode(
            String shortCode,
            String baseUrl,
            String authenticatedEmail) {

        UrlMapping mapping = repo
                .findByShortCodeAndUser_EmailIgnoreCase(
                        shortCode,
                        authenticatedEmail
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL not found"
                ));

        return toResponse(mapping, baseUrl);
    }


    // GET ALL URLS OF AUTHENTICATED USER
    public List<UrlResponse> getAllUrls(
            String baseUrl,
            String authenticatedEmail) {

        return repo
                .findAllByUser_EmailIgnoreCase(authenticatedEmail)
                .stream()
                .map(mapping -> toResponse(mapping, baseUrl))
                .toList();
    }


    // DELETE URL
    public void deleteByShortCode(
            String shortCode,
            String authenticatedEmail) {

        UrlMapping mapping = repo
                .findByShortCodeAndUser_EmailIgnoreCase(
                        shortCode,
                        authenticatedEmail
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL not found"
                ));

        repo.delete(mapping);
    }


    // ENABLE / DISABLE URL
    public void updateStatus(
            String shortCode,
            UpdateUrlStatusRequest request,
            String authenticatedEmail) {

        UrlMapping mapping = repo
                .findByShortCodeAndUser_EmailIgnoreCase(
                        shortCode,
                        authenticatedEmail
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL not found"
                ));

        mapping.setActive(request.getActive());

        repo.save(mapping);
    }


    // UPDATE URL SETTINGS
    public UrlResponse updateUrlSettings(
            String shortCode,
            UpdateUrlRequest request,
            String baseUrl,
            String authenticatedEmail) {

        UrlMapping mapping = repo
                .findByShortCodeAndUser_EmailIgnoreCase(
                        shortCode,
                        authenticatedEmail
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL not found"
                ));

        mapping.setOrginalUrl(request.getOriginalUrl());
        mapping.setExpiresAt(request.getExpiresAt());
        mapping.setActive(request.getActive());

        // Short code cannot be changed here

        UrlMapping updated = repo.save(mapping);

        return toResponse(updated, baseUrl);
    }


    // PUBLIC REDIRECTION
    public String getOriginalUrl(String shortCode) {

        UrlMapping mapping = repo
                .findByShortCode(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Short URL not found"
                ));

        if (!mapping.isActive()) {

            throw new ResourceNotFoundException(
                    "This short URL has been disabled"
            );
        }

        if (mapping.getExpiresAt() != null
                && !mapping.getExpiresAt().isAfter(LocalDateTime.now())) {

            throw new ResourceNotFoundException(
                    "This short URL has expired"
            );
        }

        return mapping.getOrginalUrl();
    }

}