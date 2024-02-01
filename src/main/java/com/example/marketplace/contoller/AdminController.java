package com.example.marketplace.contoller;


import com.example.marketplace.model.User;
import com.example.marketplace.model.enums.Roles;
import com.example.marketplace.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN_ROLE')")
public class AdminController {
    private final UserService userService;
    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("users",userService.userList());
        return "admin";
    }

    @PostMapping("/user/ban/{id}")
    public String userBan(@PathVariable("id")Long id){
        userService.banUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model) {
        model.addAttribute("user",user);
        model.addAttribute("roles", Roles.values());
        return "userEdit";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") User user,@RequestParam Map<String, String> form,Model model) {

        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

}
