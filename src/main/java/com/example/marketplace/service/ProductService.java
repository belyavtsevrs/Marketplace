package com.example.marketplace.service;


import com.example.marketplace.model.Image;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.ProductRepository;
import com.example.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public void addProduct(Principal principal ,
                           Product product,
                           MultipartFile file1 ,
                           MultipartFile file2 ,
                           MultipartFile file3
    ){
        product.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if(file1.getSize() > 0){
            image1 = toImage(file1);
            image1.setPreviewImage(true);
            product.addImage(image1);
        }
        if(file2.getSize() > 0){
            image2 = toImage(file2);
            product.addImage(image2);
        }
        if(file3.getSize() > 0){
            image3 = toImage(file3);
            product.addImage(image3);
        }
        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail());
        Product productDB = productRepository.save(product);
        if(!productDB.getImageList().isEmpty()) {
            productDB.setIsPreviewImage(productDB.getImageList().get(0).getId());
        }
        productRepository.save(productDB);
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
    public List<Product> getProductList(){
        return productRepository.findAll();
    }
    public void removeById(Long id){
        productRepository.deleteById(id);
    }
    public User getUserByPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElse(new User());
    }
    private Image toImage(MultipartFile file){
        try {
            Image image = new Image();
            image.setOriginalFileName(file.getOriginalFilename());
            image.setContentType(file.getContentType());
            image.setName(file.getName());
            image.setSize(file.getSize());
            image.setData(file.getBytes());
            return image;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }
}
