package com.sericulture.repository;

import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.entity.ChowkiManagement;
import com.sericulture.model.api.ChowkiManagementResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            " U.name AS tscName," +
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
            " CM.farmerId," +
            " CM.isVerified," +
            " CM.receiptNo" +
            ")\n" +
            " from ChowkiManagement CM\n"+
            " LEFT JOIN Farmer f ON f.farmerId=CM.farmerId\n"+
            " LEFT JOIN Village V ON V.villageId=CM.village\n"+
            " LEFT JOIN District D ON D.districtId=CM.district\n"+
            " LEFT JOIN State S ON S.stateId=CM.state\n"+
            " LEFT JOIN Taluk T ON T.talukId=CM.taluk\n"+
            " LEFT JOIN Hobli H ON H.hobliId=CM.hobli\n"+
            " LEFT JOIN RaceMaster R ON R.raceId=CM.raceOfDfls\n"+
            " LEFT JOIN TscMaster U ON U.tscMasterId=CM.tsc\n"+
            " where CM.userMasterId = :userMasterId\n"+
            " AND CM.chowkiId = :chowkiId"
    )
    Optional<ChowkiManagementByIdDTO> findByChowkiIdAndUserMasterId(Integer chowkiId, Long userMasterId);

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
            " U.name AS tscName," +
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
            " CM.farmerId," +
            " CM.isVerified," +
            " CM.receiptNo" +
            ")\n" +
            " from ChowkiManagement CM\n"+
            " LEFT JOIN Farmer f ON f.farmerId=CM.farmerId\n"+
            " LEFT JOIN Village V ON V.villageId=CM.village\n"+
            " LEFT JOIN District D ON D.districtId=CM.district\n"+
            " LEFT JOIN State S ON S.stateId=CM.state\n"+
            " LEFT JOIN Taluk T ON T.talukId=CM.taluk\n"+
            " LEFT JOIN Hobli H ON H.hobliId=CM.hobli\n"+
            " LEFT JOIN RaceMaster R ON R.raceId=CM.raceOfDfls\n"+
            " LEFT JOIN TscMaster U ON U.tscMasterId=CM.tsc\n"+
            " where CM.userMasterId = :userMasterId\n"+
            " order by CM.chowkiId DESC"
    )
    List<ChowkiManagementByIdDTO> getByUserMasterIdOrderByChowkiIdDesc(Long userMasterId);

    @Query(value = "SELECT next value for dbo.chowkireceipt_seq", nativeQuery = true)
    public BigDecimal getNextValRecieptSequence();

    @Query("SELECT DISTINCT(f.farmerId) FROM Farmer f LEFT JOIN ChowkiManagement cm ON cm.farmerId = f.farmerId WHERE f.fruitsId = :fruitsId")
    Optional<Long> findFarmerIdByFruitsId(@Param("fruitsId") String fruitsId);

    @Query("SELECT DISTINCT(f.farmerId) FROM Farmer f LEFT JOIN ChowkiManagement cm ON cm.farmerId = f.farmerId WHERE f.fruitsId = :fruitsId and cm.chowkiId = :chowkiId")
    Optional<Long> findFarmerIdByFruitsIdAndChawkiId(@Param("fruitsId") String fruitsId, @Param("chowkiId") Integer chowkiId);

    @Query(nativeQuery = true, value = """
    SELECT cm.chowki_id, cm.lot_numbers_crc, cm.lot_numbers_of_the_rsp, cm.numbers_of_dfls,
    cm.rate_per_100_dfls, cm.race_of_dfls, cm.source_of_dfls, rm.race_name
    FROM chowki_management cm
    LEFT JOIN race_master rm ON cm.race_of_dfls = rm.race_id
    WHERE cm.farmer_id =:farmerId
    AND cm.isverified = 0;
    """)

    public List<Object[]> getChawkiDetailsByFarmerId(Long farmerId);

    @Query(nativeQuery = true, value = """
    SELECT cm.chowki_id, cm.lot_numbers_crc, cm.lot_numbers_of_the_rsp, cm.numbers_of_dfls,
    cm.rate_per_100_dfls, cm.race_of_dfls, cm.source_of_dfls,cm.hatching_date, rm.race_name
    FROM chowki_management cm
    LEFT JOIN race_master rm ON cm.race_of_dfls = rm.race_id
    WHERE cm.farmer_id =:farmerId
    AND cm.isverified = 1;
    """)

    public List<Object[]> getInspectioninfoForFarmer(Long farmerId);


    @Query(nativeQuery = true, value = """
    SELECT cm.chowki_id, cm.lot_numbers_crc, cm.lot_numbers_of_the_rsp, cm.numbers_of_dfls,
    cm.rate_per_100_dfls, cm.race_of_dfls, cm.source_of_dfls,cm.hatching_date, rm.race_name
    FROM chowki_management cm
    LEFT JOIN race_master rm ON cm.race_of_dfls = rm.race_id
    WHERE cm.fruits_id =:fruitsId
    AND cm.is_sale_tracked = 0;
    """)
    public List<Object[]> getInspectioninfoForCocoonTrack(String fruitsId);

    @Query(nativeQuery = true, value = """
     SELECT Distinct
       sadod.id,
       sadod.rate_per100dfls_price,
       sadod.number_of_dfls_disposed,
       sadod.lot_number,
       sadod.expected_date_of_hatching,
       sadod.race_id,
       rm.race_name,
       lg.auction_date,
       lg.lot_weight
   FROM sale_and_disposal_of_dfls sadod
   LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
   LEFT JOIN market_auction ma ON ma.lot_Parental_Level = sadod.lot_number
   LEFT JOIN lot_groupage lg ON lg.market_auction_id = ma.market_auction_id
   WHERE sadod.fruits_id = :fruitsId
   AND sadod.is_sale_tracked = 0;
   """)
    public List<Object[]> getInspectioninfoForCocoonSaleTrack(String fruitsId);


}
