package com.bookworm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookworm.dto.request.AssignBeneficiariesRequestDTO;
import com.bookworm.dto.response.ProductBeneficiaryResponseDTO;

@Service
public interface ProductBeneficiaryService {
	void assignBeneficiariesToProduct(AssignBeneficiariesRequestDTO requestDTO);
	List<ProductBeneficiaryResponseDTO> getBeneficiariesForProduct(Integer productId);

}

