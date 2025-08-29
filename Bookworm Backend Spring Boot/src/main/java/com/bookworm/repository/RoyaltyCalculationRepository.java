package com.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookworm.entities.RoyaltyCalculation;

@Repository
public interface RoyaltyCalculationRepository extends JpaRepository<RoyaltyCalculation, Integer> {
    // You can add custom queries here if needed later
}
