package com.bookworm.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookworm.entities.Invoice;
import com.bookworm.entities.InvoiceDetail;
import com.bookworm.entities.Product;
import com.bookworm.entities.ProductBeneficiary;
import com.bookworm.entities.RoyaltyCalculation;
import com.bookworm.repository.InvoiceDetailRepository;
import com.bookworm.repository.ProductBeneficiaryRepository;
import com.bookworm.repository.RoyaltyCalculationRepository;
import com.bookworm.service.RoyaltyCalculationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoyaltyCalculationServiceImpl implements RoyaltyCalculationService {

    private final InvoiceDetailRepository invoiceDetailsRepository;
    private final ProductBeneficiaryRepository productBeneficiaryRepository;
    private final RoyaltyCalculationRepository royaltyCalculationRepository;

    @Transactional
    @Override
    public void calculateRoyaltyForInvoice(Invoice invoice) {
        // Step 1: Fetch all invoice items (products) in the given invoice
        List<InvoiceDetail> invoiceItems = invoiceDetailsRepository.findByInvoice(invoice);

        if (invoiceItems == null || invoiceItems.isEmpty()) {
            System.out.println("🚫 No invoice details found for invoice ID: " + invoice.getInvoiceId());
            return;
        }

        for (InvoiceDetail item : invoiceItems) {
            Product product = item.getProduct();
            BigDecimal salesPrice = item.getSellPrice(); // price paid for that item
            String transactionType = item.getTranType(); // PURCHASE / RENTAL

            if (product == null) {
                System.out.println("⚠️ Skipping item with null product in invoice detail.");
                continue;
            }

            // Step 2: Get all beneficiaries for this product
            List<ProductBeneficiary> beneficiaries = productBeneficiaryRepository.findByProduct(product);

            if (beneficiaries == null || beneficiaries.isEmpty()) {
                System.out.println("🔍 No beneficiaries found for product ID: " + product.getId());
                continue;
            }

            for (ProductBeneficiary pb : beneficiaries) {
                BigDecimal percentage = pb.getPercentage();

                if (percentage == null || percentage.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("❌ Skipping invalid percentage for beneficiary ID: " + pb.getBeneficiary().getBenId());
                    continue;
                }

                BigDecimal royaltyAmount = salesPrice.multiply(percentage).divide(BigDecimal.valueOf(100));

                // Step 3: Save the royalty calculation record
                RoyaltyCalculation royalty = RoyaltyCalculation.builder()
                        .beneficiary(pb.getBeneficiary())
                        .product(product)
                        .invoice(invoice)
                        .royaltyDate(LocalDate.now())
                        .transactionType(transactionType)
                        .salesPrice(salesPrice)
                        .royaltyOnSalesPrice(royaltyAmount)
                        .build();

                royaltyCalculationRepository.save(royalty);

                System.out.println("✅ Royalty saved: " + pb.getBeneficiary().getBenName() + " -> ₹" + royaltyAmount);
            }
        }

        System.out.println("🎉 Royalty calculation completed for invoice ID: " + invoice.getInvoiceId());
    }
}
