package com.bookworm.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_dtl_id")
    private Integer invDtlId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "rent_no_of_days")
    private Integer rentNoOfDays;

    @Column(name = "royalty_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal royaltyAmount;

    @Column(name = "sell_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal sellPrice;

    @Column(name = "tran_type", nullable = false, length = 20)
    private String tranType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    // 👉 Default Constructor
    public InvoiceDetail() {
    }

    // 👉 All-Args Constructor
    public InvoiceDetail(Integer invDtlId, Integer quantity, Integer rentNoOfDays,
                         BigDecimal royaltyAmount, BigDecimal sellPrice, String tranType,
                         Product product, Invoice invoice) {
        this.invDtlId = invDtlId;
        this.quantity = quantity;
        this.rentNoOfDays = rentNoOfDays;
        this.royaltyAmount = royaltyAmount;
        this.sellPrice = sellPrice;
        this.tranType = tranType;
        this.product = product;
        this.invoice = invoice;
    }

    // 👉 Getters and Setters

    public Integer getInvDtlId() {
        return invDtlId;
    }

    public void setInvDtlId(Integer invDtlId) {
        this.invDtlId = invDtlId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRentNoOfDays() {
        return rentNoOfDays;
    }

    public void setRentNoOfDays(Integer rentNoOfDays) {
        this.rentNoOfDays = rentNoOfDays;
    }

    public BigDecimal getRoyaltyAmount() {
        return royaltyAmount;
    }

    public void setRoyaltyAmount(BigDecimal royaltyAmount) {
        this.royaltyAmount = royaltyAmount;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
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
}
