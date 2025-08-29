package com.bookworm.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
@Table(name = "royalty_calculation")
public class RoyaltyCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "royalty_id")
    private Integer royaltyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id", nullable = false)
    private BeneficiaryMaster beneficiary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(name = "royalty_date", nullable = false)
    private LocalDate royaltyDate;

    @Column(name = "transaction_type", nullable = false, length = 20)
    private String transactionType;

    @Column(name = "sales_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal salesPrice;

    @Column(name = "royalty_on_sales_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal royaltyOnSalesPrice;

    // === Constructors ===

    public RoyaltyCalculation() {
    }

    public RoyaltyCalculation(Integer royaltyId, BeneficiaryMaster beneficiary, Product product,
                              Invoice invoice, LocalDate royaltyDate, String transactionType,
                              BigDecimal salesPrice, BigDecimal royaltyOnSalesPrice) {
        this.royaltyId = royaltyId;
        this.beneficiary = beneficiary;
        this.product = product;
        this.invoice = invoice;
        this.royaltyDate = royaltyDate;
        this.transactionType = transactionType;
        this.salesPrice = salesPrice;
        this.royaltyOnSalesPrice = royaltyOnSalesPrice;
    }

    // === Getters and Setters ===

    public Integer getRoyaltyId() {
        return royaltyId;
    }

    public void setRoyaltyId(Integer royaltyId) {
        this.royaltyId = royaltyId;
    }

    public BeneficiaryMaster getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryMaster beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public LocalDate getRoyaltyDate() {
        return royaltyDate;
    }

    public void setRoyaltyDate(LocalDate royaltyDate) {
        this.royaltyDate = royaltyDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getRoyaltyOnSalesPrice() {
        return royaltyOnSalesPrice;
    }

    public void setRoyaltyOnSalesPrice(BigDecimal royaltyOnSalesPrice) {
        this.royaltyOnSalesPrice = royaltyOnSalesPrice;
    }
}
