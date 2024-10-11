package com.sericulture.repository;

import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.api.response.SupplyOfDisinfectantsResponse;
import com.sericulture.model.entity.FitnessCertificate;
import com.sericulture.model.entity.SupplyOfDisinfectants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SupplyOfDisinfectantsRepository extends JpaRepository<SupplyOfDisinfectants, Long> {

    public SupplyOfDisinfectants findBySupplyOfDisinfectantsIdAndUserMasterIdAndActive(long supplyOfDisinfectantsId,long userMasterId, boolean isActive);

    List<SupplyOfDisinfectantsResponse> getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(Long userMasterId);

}
