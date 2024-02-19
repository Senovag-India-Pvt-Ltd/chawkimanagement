package com.sericulture.repository;

import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.entity.ChowkiManagement;
import com.sericulture.model.api.ChowkiManagementResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ChowkiManagementRepository extends JpaRepository<ChowkiManagement, Integer> {

    @Query("select new com.sericulture.model.api.ChowkiManagementByIdDTO(" +
            " CM.chowkiId," +
            " CM.farmerName," +
            " CM.fatherName," +
            " CM.fruitsId," +
            " CM.dflsSource," +
            " CM.raceOfDfls," +
            " R.raceName," +
            " CM.numbersOfDfls," +
            " CM.lotNumberRsp," +
            " CM.lotNumberCrc," +
            " V.villageName," +
            " D.districtName," +
            " S.stateName," +
            " T.talukName," +
            " H.hobliName," +
            " U.tscName," +
            " CM.village," +
            " CM.district," +
            " CM.state," +
            " CM.taluk," +
            " CM.hobli," +
            " CM.tsc," +
            " CM.soldAfter1stOr2ndMould," +
            " CM.ratePer100Dfls," +
            " CM.price," +
            " CM.hatchingDate," +
            " CM.dispatchDate," +
            " CM.receiptNo" +
            ")\n" +
            " from ChowkiManagement CM\n"+
            " LEFT JOIN Village V ON V.villageId=CM.village\n"+
            " LEFT JOIN District D ON D.districtId=CM.district\n"+
            " LEFT JOIN State S ON S.stateId=CM.state\n"+
            " LEFT JOIN Taluk T ON T.talukId=CM.taluk\n"+
            " LEFT JOIN Hobli H ON H.hobliId=CM.hobli\n"+
            " LEFT JOIN RaceMaster R ON R.raceId=CM.raceOfDfls\n"+
            " LEFT JOIN UserMaster U ON U.userMasterId=CM.tsc\n"+
            " where CM.userMasterId = :userMasterId\n"+
            " AND CM.chowkiId = :chowkiId"
    )
    Optional<ChowkiManagementByIdDTO> findByChowkiIdAndUserMasterId(Integer chowkiId, Long userMasterId);

    @Query("select new com.sericulture.model.api.ChowkiManagementResponse(" +
            " CM.chowkiId," +
            " CM.farmerName," +
            " CM.fatherName," +
            " CM.fruitsId," +
            " CM.dflsSource," +
            " R.raceName," +
            " CM.numbersOfDfls," +
            " CM.lotNumberRsp," +
            " CM.lotNumberCrc," +
            " V.villageName," +
            " D.districtName," +
            " S.stateName," +
            " T.talukName," +
            " H.hobliName," +
            " U.tscName," +
            " CM.soldAfter1stOr2ndMould," +
            " CM.ratePer100Dfls," +
            " CM.price," +
            " CM.hatchingDate," +
            " CM.dispatchDate," +
            " CM.receiptNo" +
            ")\n" +
            " from ChowkiManagement CM\n"+
            " LEFT JOIN Village V ON V.villageId=CM.village\n"+
            " LEFT JOIN District D ON D.districtId=CM.district\n"+
            " LEFT JOIN State S ON S.stateId=CM.state\n"+
            " LEFT JOIN Taluk T ON T.talukId=CM.taluk\n"+
            " LEFT JOIN Hobli H ON H.hobliId=CM.hobli\n"+
            " LEFT JOIN RaceMaster R ON R.raceId=CM.raceOfDfls\n"+
            " LEFT JOIN UserMaster U ON U.userMasterId=CM.tsc\n"+
            " where CM.userMasterId = :userMasterId\n"+
            " order by CM.chowkiId DESC"
    )
    List<ChowkiManagementResponse> getByUserMasterIdOrderByChowkiIdDesc(Long userMasterId);

    @Query(value = "SELECT next value for dbo.chowkireceipt_seq", nativeQuery = true)
    public BigDecimal getNextValRecieptSequence();

}
