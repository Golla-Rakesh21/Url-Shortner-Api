package com.learning.UrlShortner.repo;

import com.learning.UrlShortner.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping,Long> {

    Optional<UrlMapping> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    // Find URL belonging to a particular user
    Optional<UrlMapping> findByShortCodeAndUser_EmailIgnoreCase(
            String shortCode,
            String email
    );

    // Get all URLs belonging to a particular user
    List<UrlMapping> findAllByUser_EmailIgnoreCase(String email);

    // Check custom alias
    boolean existsByShortCodeAndUser_EmailIgnoreCase(
            String shortCode,
            String email
    );
}
