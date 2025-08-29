package com.bookworm.controller;

import com.bookworm.dto.request.BeneficiaryRequestDTO;
import com.bookworm.entities.BeneficiaryMaster;
import com.bookworm.service.BeneficiaryMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryMasterController {

    private final BeneficiaryMasterService beneficiaryMasterService;

    @PostMapping
    public ResponseEntity<BeneficiaryMaster> createBeneficiary(@RequestBody BeneficiaryRequestDTO requestDTO) {
        BeneficiaryMaster newBeneficiary = beneficiaryMasterService.createBeneficiary(requestDTO);
        return ResponseEntity.ok(newBeneficiary);
    }
}