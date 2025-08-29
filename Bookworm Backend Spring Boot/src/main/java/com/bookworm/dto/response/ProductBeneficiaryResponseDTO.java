package com.bookworm.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  
public class ProductBeneficiaryResponseDTO {
	private Integer beneficiaryId;
    private String beneficiaryName;  // 👈 not present in earlier DTO
    private BigDecimal royaltyPercentage;
}
