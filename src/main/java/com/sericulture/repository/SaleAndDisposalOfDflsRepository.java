package com.sericulture.repository;


import com.sericulture.model.entity.SaleAndDisposalOfDfls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SaleAndDisposalOfDflsRepository extends JpaRepository<SaleAndDisposalOfDfls, Integer> {

    Optional<SaleAndDisposalOfDfls> findByIdAndActive(Integer id,boolean isActive);

}
