package com.bookworm.repository;

import com.bookworm.entities.Invoice;
import com.bookworm.entities.InvoiceDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Repository for accessing InvoiceDetail entities. Required to fetch the
 * price paid for purchased items.
 */
@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {
    List<InvoiceDetail> findByInvoice(Invoice invoice);

}

