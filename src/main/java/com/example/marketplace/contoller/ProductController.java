package com.example.marketplace.contoller;

import com.example.marketplace.model.Product;
import com.example.marketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/")
    public String product(Model model,Principal principal,Product product){
        model.addAttribute("products",productService.getProductList());
        model.addAttribute("user",productService.getUserByPrincipal(principal));
        return "product";
    }
    @PostMapping("/")
    public String addProduct(Product product, Principal principal,
                             @RequestParam("file1")MultipartFile file1,
                             @RequestParam("file2")MultipartFile file2,
                             @RequestParam("file3")MultipartFile file3
    )
    {
        productService.addProduct(principal,product,file1,file2,file3);
        return "redirect:/";
    }
    @GetMapping("/productInfo/{id}")
    public String productInfo(@PathVariable Long id,Model model){
        model.addAttribute("product",productService.getProductById(id));
        model.addAttribute("image",productService.getProductById(id).getImageList());
        return "productInfo";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.removeById(id);
        return "redirect:/";
    }
}
