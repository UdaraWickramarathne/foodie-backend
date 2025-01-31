package com.foodie.foodservice.repository;

import com.foodie.foodservice.dto.ProductDTO;
import com.foodie.foodservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT new com.foodie.foodservice.dto.ProductDTO(p.id, p.name, p.description, p.category, p.price, p.imageName, p.imageType) FROM Product p")
    List<ProductDTO> findAllWithoutImageData();


    @Query("SELECT new com.foodie.foodservice.dto.ProductDTO(p.id, p.name, p.description, p.category, p.price, p.imageName, p.imageType) FROM Product p WHERE "+
    "LOWER" + "(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductDTO> searchProducts(String keyword);

}
