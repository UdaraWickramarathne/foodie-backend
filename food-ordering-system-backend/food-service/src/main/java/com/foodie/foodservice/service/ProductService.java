package com.foodie.foodservice.service;

import com.foodie.foodservice.dto.ProductDTO;
import com.foodie.foodservice.model.Product;
import com.foodie.foodservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {


    final
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public String addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageData(imageFile.getBytes());
        productRepository.save(product);
        return "Product added successfully!";

    }

    public Product getProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<ProductDTO> getAllProductsWithoutImage() {
        return productRepository.findAllWithoutImageData();
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageData(imageFile.getBytes());
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
