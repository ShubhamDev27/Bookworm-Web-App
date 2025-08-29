package com.bookworm.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
@Table(name = "product_beneficiary")
public class ProductBeneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_beneficiary_id")
    private Integer id;

    @Column(name = "percentage", nullable = false, precision = 10, scale = 2)
    private BigDecimal percentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id", nullable = false)
    private BeneficiaryMaster beneficiary;

    // === Constructors ===

    public ProductBeneficiary() {
    }

    public ProductBeneficiary(Integer id, BigDecimal percentage, Product product, BeneficiaryMaster beneficiary) {
        this.id = id;
        this.percentage = percentage;
        this.product = product;
        this.beneficiary = beneficiary;
    }

    // === Getters and Setters ===

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BeneficiaryMaster getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryMaster beneficiary) {
        this.beneficiary = beneficiary;
    }
}
