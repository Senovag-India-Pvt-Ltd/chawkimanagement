package com.sericulture.repository;


import com.sericulture.model.api.response.MgnregaSchemeResponse;
import com.sericulture.model.api.response.SupplyOfDisinfectantsResponse;
import com.sericulture.model.entity.MgnregaScheme;
import com.sericulture.model.entity.SupplyOfDisinfectants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MgnregaSchemeRepository extends JpaRepository<MgnregaScheme, Long> {

    List<MgnregaScheme> findByActiveOrderByMgnregaSchemeIdDesc(boolean isActive);

    public MgnregaScheme findByMgnregaSchemeIdAndActive(long mgnregaSchemeId,boolean isActive);
}
