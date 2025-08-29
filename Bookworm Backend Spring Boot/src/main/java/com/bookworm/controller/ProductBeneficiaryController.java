package com.bookworm.controller;
import com.bookworm.dto.request.AssignBeneficiariesRequestDTO;
import com.bookworm.dto.response.ProductBeneficiaryResponseDTO; // Correct import
import com.bookworm.service.ProductBeneficiaryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductBeneficiaryController {

    private final ProductBeneficiaryService productBeneficiaryService;
    // Removed ProductBeneficiaryRepository from the controller

    @PostMapping("/assign-beneficiaries")
    public ResponseEntity<String> assignBeneficiariesToProduct(
            @RequestBody AssignBeneficiariesRequestDTO requestDTO) {
        
        productBeneficiaryService.assignBeneficiariesToProduct(requestDTO);
        return ResponseEntity.ok("Beneficiaries assigned successfully.");
    }

    // Corrected method to delegate to the service layer
    @GetMapping("/{productId}/beneficiaries")
    public ResponseEntity<List<ProductBeneficiaryResponseDTO>> getBeneficiariesByProduct(@PathVariable Integer productId) {
        // Delegate the call directly to the service layer
        List<ProductBeneficiaryResponseDTO> response = productBeneficiaryService.getBeneficiariesForProduct(productId);
        return ResponseEntity.ok(response);
    }
}