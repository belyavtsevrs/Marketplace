package com.example.marketplace.contoller;


import com.example.marketplace.model.User;
import com.example.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login")
    public String redirectAfteLogin(){
        return "redirect:/";
    }
    @GetMapping("/registration")
    public String createRegistrationForm(User user){
        return "registration";
    }
    @PostMapping("/registration")
    public String registration(User user){
        userService.createUser(user);
        return "redirect:/login";
    }
    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "userInfo";
    }
}
