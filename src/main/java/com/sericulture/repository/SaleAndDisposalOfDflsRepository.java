package com.sericulture.repository;


import com.sericulture.model.entity.SaleAndDisposalOfDfls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SaleAndDisposalOfDflsRepository extends JpaRepository<SaleAndDisposalOfDfls, Integer> {

    Optional<SaleAndDisposalOfDfls> findByIdAndActive(Integer id,boolean isActive);

    public SaleAndDisposalOfDfls findByFruitsIdAndIdAndActiveIn(@Param("fruitsId") String fruitsId,@Param("id") long id, @Param("active") Set<Boolean> active);

}
