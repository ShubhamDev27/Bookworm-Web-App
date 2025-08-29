package com.bookworm.dto.request;

import java.util.List;

public class AssignBeneficiariesRequestDTO {

    private Integer productId;
    private List<BeneficiaryRoyaltyDTO> beneficiaries;

    // No-args constructor
    public AssignBeneficiariesRequestDTO() {
    }

    // All-args constructor
    public AssignBeneficiariesRequestDTO(Integer productId, List<BeneficiaryRoyaltyDTO> beneficiaries) {
        this.productId = productId;
        this.beneficiaries = beneficiaries;
    }

    // Getter and Setter for productId
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    // Getter and Setter for beneficiaries
    public List<BeneficiaryRoyaltyDTO> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<BeneficiaryRoyaltyDTO> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }
}
