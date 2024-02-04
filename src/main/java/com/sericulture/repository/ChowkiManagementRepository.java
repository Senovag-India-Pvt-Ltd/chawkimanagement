package com.sericulture.repository;

import com.sericulture.model.ChowkiManagement;
import com.sericulture.model.dto.ChowkiManagementDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChowkiManagementRepository extends JpaRepository<ChowkiManagement, Integer> {

    @Query("select new com.sericulture.model.dto.ChowkiManagementDTO(" +
            " CM.chowkiId," +
            " CM.farmerName," +
            " CM.fatherName," +
            " CM.fruitsId," +
            " CM.dflsSource," +
            " CM.raceOfDfls," +
            " CM.numbersOfDfls," +
            " CM.lotNumberRsp," +
            " CM.lotNumberCrc," +
            " CM.village," +
            " CM.district," +
            " CM.state," +
            " CM.tsc," +
            " CM.soldAfter1stOr2ndMould," +
            " CM.ratePer100Dfls," +
            " CM.price," +
            " CM.dispatchDate" +
            ")\n" +
            " from ChowkiManagement CM\n"+
            " where CM.userMasterId = :userMasterId\n"+
            " AND CM.chowkiId = :chowkiId"
    )
    Optional<ChowkiManagementDTO> findByChowkiIdAndUserMasterId(Integer chowkiId,Long userMasterId);

    @Query("select new com.sericulture.model.dto.ChowkiManagementDTO(" +
            " CM.chowkiId," +
            " CM.farmerName," +
            " CM.fatherName," +
            " CM.fruitsId," +
            " CM.dflsSource," +
            " CM.raceOfDfls," +
            " CM.numbersOfDfls," +
            " CM.lotNumberRsp," +
            " CM.lotNumberCrc," +
            " CM.village," +
            " CM.district," +
            " CM.state," +
            " CM.tsc," +
            " CM.soldAfter1stOr2ndMould," +
            " CM.ratePer100Dfls," +
            " CM.price," +
            " CM.dispatchDate" +
            ")\n" +
            " from ChowkiManagement CM\n"+
            " where CM.userMasterId = :userMasterId\n"+
            " order by CM.chowkiId DESC"
    )
    List<ChowkiManagementDTO> getByUserMasterIdOrderByChowkiIdDesc(Long userMasterId);

}
