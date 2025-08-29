package com.bookworm.repository;

import com.bookworm.entities.BeneficiaryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryMasterRepository extends JpaRepository<BeneficiaryMaster, Integer> {
}
