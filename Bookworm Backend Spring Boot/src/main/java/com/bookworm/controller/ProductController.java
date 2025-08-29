package com.bookworm.controller;

import com.bookworm.dto.request.ProductRequestDTO;
import com.bookworm.dto.response.ProductResponseDTO;
import com.bookworm.service.ProductService;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Integer id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Only users with ROLE_ADMIN can access this
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO createdProduct = productService.createProduct(productRequestDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Integer id, @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search/by-author")
    public ResponseEntity<List<ProductResponseDTO>> findProductsByAuthor(@RequestParam("author") String authorName) {
        List<ProductResponseDTO> products = productService.findProductsByAuthor(authorName);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/category/{genreId}")
    public ResponseEntity<List<ProductResponseDTO>> findProductsByCategory(@PathVariable Integer genreId) {
        List<ProductResponseDTO> products = productService.findProductsByGenre(genreId);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/search/by-name")
    public ResponseEntity<List<ProductResponseDTO>> findProductsByName(@RequestParam("name") String productName) {
        List<ProductResponseDTO> products = productService.findProductsByName(productName);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/language/{languageId}")
    public ResponseEntity<List<ProductResponseDTO>> findProductsByLanguage(@PathVariable Integer languageId) {
        List<ProductResponseDTO> products = productService.findProductsByLanguage(languageId);
        return ResponseEntity.ok(products);
    }
 
    
}