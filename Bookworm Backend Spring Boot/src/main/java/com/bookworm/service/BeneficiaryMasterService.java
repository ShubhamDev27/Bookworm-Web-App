package com.bookworm.service;

import org.springframework.stereotype.Service;

import com.bookworm.dto.request.BeneficiaryRequestDTO;
import com.bookworm.entities.BeneficiaryMaster;
import com.bookworm.repository.BeneficiaryMasterRepository;

import lombok.RequiredArgsConstructor;

@Service
	@RequiredArgsConstructor
	public class BeneficiaryMasterService {

	    private final BeneficiaryMasterRepository beneficiaryMasterRepository;

	    public BeneficiaryMaster createBeneficiary(BeneficiaryRequestDTO requestDTO) {
	        BeneficiaryMaster beneficiary = new BeneficiaryMaster();
	        beneficiary.setBenName(requestDTO.getBenName());
	        beneficiary.setBenEmail(requestDTO.getBenEmail());
	        beneficiary.setBenPan(requestDTO.getBenPan());
	        
	        return beneficiaryMasterRepository.save(beneficiary);
	    }
}
