package com.sericulture.repository;


import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.response.MgnregaSchemeResponse;
import com.sericulture.model.api.response.SupplyOfDisinfectantsResponse;
import com.sericulture.model.entity.MgnregaScheme;
import com.sericulture.model.entity.SupplyOfDisinfectants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MgnregaSchemeRepository extends JpaRepository<MgnregaScheme, Long> {

    List<MgnregaScheme> findByActiveOrderByMgnregaSchemeIdDesc(boolean isActive);

    public MgnregaScheme findByMgnregaSchemeIdAndActive(long mgnregaSchemeId,boolean isActive);

    Optional<MgnregaSchemeResponse> findByMgnregaSchemeId(long mgnregaSchemeId);
}
