package com.bookworm.repository;


import com.bookworm.entities.Product;
import com.bookworm.entities.ProductBeneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBeneficiaryRepository extends JpaRepository<ProductBeneficiary, Integer> {

    List<ProductBeneficiary> findByProduct(Product product);

    void deleteByProduct(Product product);
 // useful for reassignment
}
