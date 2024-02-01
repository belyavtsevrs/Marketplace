package com.example.marketplace.security;

import com.example.marketplace.model.User;
import com.example.marketplace.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException(String.format("User '%s' not found",username))
        );
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                         user.getPassword(),
                user.getRoles()
        );
    }
}
