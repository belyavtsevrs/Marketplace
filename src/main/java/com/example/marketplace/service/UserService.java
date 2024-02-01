package com.example.marketplace.service;


import com.example.marketplace.model.User;
import com.example.marketplace.model.enums.Roles;
import com.example.marketplace.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public boolean createUser(User user){
        user.setActive(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(user.getName().equalsIgnoreCase("ADMIN")){
            user.getRoles().add(Roles.ADMIN_ROLE);
        }
        user.getRoles().add(Roles.USER_ROLE);
        userRepository.save(user);
        return true;
    }
    public List<User> userList(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException(String.format("User with id '$s' not found ",id))
        );
    }
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Roles.values())
                        .map(Roles::name)
                                .collect(Collectors.toSet());
        user.getRoles().clear();
        for(var x: form.entrySet()){
            log.info("form's key= {},value = {}",x.getKey(),x.getValue());
           if(x.getValue().equals("on")){
               user.getRoles().add(Roles.valueOf(x.getKey()));
           }
        }
        userRepository.save(user);
    }
    public void banUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }
}
