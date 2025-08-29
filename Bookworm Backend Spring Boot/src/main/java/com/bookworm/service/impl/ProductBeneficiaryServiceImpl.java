package com.bookworm.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookworm.dto.request.AssignBeneficiariesRequestDTO;
import com.bookworm.dto.request.BeneficiaryRoyaltyDTO;
import com.bookworm.dto.response.ProductBeneficiaryResponseDTO;
import com.bookworm.entities.BeneficiaryMaster;
import com.bookworm.entities.Product;
import com.bookworm.entities.ProductBeneficiary;
import com.bookworm.repository.BeneficiaryMasterRepository;
import com.bookworm.repository.ProductBeneficiaryRepository;
import com.bookworm.repository.ProductRepository;
import com.bookworm.service.ProductBeneficiaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductBeneficiaryServiceImpl implements ProductBeneficiaryService {

    private final ProductRepository productRepository;
    private final BeneficiaryMasterRepository beneficiaryMasterRepository;
    private final ProductBeneficiaryRepository productBeneficiaryRepository;

    @Transactional
    @Override
    public void assignBeneficiariesToProduct(AssignBeneficiariesRequestDTO requestDTO) {
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Delete old assignments
        productBeneficiaryRepository.deleteByProduct(product);

        // Assign new beneficiaries
        for (BeneficiaryRoyaltyDTO br : requestDTO.getBeneficiaries()) {
            BeneficiaryMaster beneficiary = beneficiaryMasterRepository.findById(br.getBeneficiaryId())
                    .orElseThrow(() -> new RuntimeException("Beneficiary not found: " + br.getBeneficiaryId()));

            ProductBeneficiary productBeneficiary = ProductBeneficiary.builder()
                    .product(product)
                    .beneficiary(beneficiary)
                    .percentage(br.getRoyaltyPercentage())
                    .build();

            productBeneficiaryRepository.save(productBeneficiary);
        }
    }

    @Override
    public List<ProductBeneficiaryResponseDTO> getBeneficiariesForProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductBeneficiary> beneficiaries = productBeneficiaryRepository.findByProduct(product);

        return beneficiaries.stream().map(pb -> ProductBeneficiaryResponseDTO.builder()
                .beneficiaryId(pb.getBeneficiary().getBenId())              // ✅ Correct
                .beneficiaryName(pb.getBeneficiary().getBenName())          // ✅ Correct
                .royaltyPercentage(pb.getPercentage())
                .build()
        ).collect(Collectors.toList());

    }
}
