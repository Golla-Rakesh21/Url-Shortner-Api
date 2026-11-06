package com.learning.UrlShortner.service;

import com.learning.UrlShortner.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository repo;
    public CustomerUserDetailsService(UserRepository userRepository) {
        this.repo = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user=repo.findByEmailIgnoreCase(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return User.withUsername(user.getEmail()).password(user.getPassword())
                .authorities("ROLE_USER").build();
    }
}
