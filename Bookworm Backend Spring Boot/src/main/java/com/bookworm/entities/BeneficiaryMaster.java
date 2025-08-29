package com.bookworm.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@Table(name = "beneficiary_master")
public class BeneficiaryMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ben_id")
    private Integer benId;

    @Column(name = "ben_name", nullable = false, length = 255)
    private String benName;

    @Column(name = "ben_email", nullable = false, length = 255, unique = true)
    private String benEmail;

    @Column(name = "ben_pan", nullable = false, length = 20, unique = true)
    private String benPan;

    // === Constructors ===

    public BeneficiaryMaster() {
    }

    public BeneficiaryMaster(Integer benId, String benName, String benEmail, String benPan) {
        this.benId = benId;
        this.benName = benName;
        this.benEmail = benEmail;
        this.benPan = benPan;
    }

    // === Getters and Setters ===

    public Integer getBenId() {
        return benId;
    }

    public void setBenId(Integer benId) {
        this.benId = benId;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenEmail() {
        return benEmail;
    }

    public void setBenEmail(String benEmail) {
        this.benEmail = benEmail;
    }

    public String getBenPan() {
        return benPan;
    }

    public void setBenPan(String benPan) {
        this.benPan = benPan;
    }
}
