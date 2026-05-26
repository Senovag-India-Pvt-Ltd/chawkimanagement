package com.sericulture.repository;

import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.entity.ChowkiManagement;
import com.sericulture.model.api.ChowkiManagementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
         SELECT
         sadod.id,
         sadod.lot_number,
         sadod.number_of_dfls_disposed,
         sadod.rate_per100dfls_price ,
         sadod.race_id,
         rm.race_name,
         sadod.expected_date_of_hatching,
         sadod.date_of_disposal,
         sadod.source_of_dfls,
         sadod.name_and_address_of_the_farm,
         f.name_kan
     FROM sale_and_disposal_of_dfls sadod
     LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
     LEFT JOIN farmer f ON sadod.fruits_id = f.fruits_id
     WHERE sadod.fruits_id = :fruitsId
     AND sadod.tsc = :tscId
     AND sadod.active = 1
     AND sadod.is_verified = 0;
    """)

    public List<Object[]> getSaleAndDisposalDetailsByFruitsId(String fruitsId,Long tscId);

    @Query(nativeQuery = true, value = """
         SELECT
         sadod.id,
         sadod.lot_number,
         sadod.number_of_dfls_disposed,
         sadod.rate_per100dfls_price ,
         sadod.race_id,
         rm.race_name,
         f.name_kan
     FROM sale_and_disposal_of_dfls_rsso sadod
     LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
     LEFT JOIN farmer f ON sadod.fruits_id = f.fruits_id
     WHERE sadod.fruits_id = :fruitsId
     AND sadod.active = 1
     AND sadod.is_verified = 0;
    """)

    public List<Object[]> getSaleAndDisposalDetailsForRssoByFruitsId(String fruitsId);

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
         SELECT
         sadod.id,
         sadod.lot_number,
         sadod.number_of_dfls_disposed,
         sadod.rate_per100dfls_price ,
         sadod.race_id,
         rm.race_name,
         sadod.expected_date_of_hatching,
         sadod.date_of_disposal,
         sadod.source_of_dfls,
         sadod.name_and_address_of_the_farm,
         f.name_kan
     FROM sale_and_disposal_of_dfls sadod
     LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
     LEFT JOIN farmer f ON sadod.fruits_id = f.fruits_id
     WHERE sadod.fruits_id = :fruitsId
     AND sadod.active = 1
     AND sadod.is_verified = 1
    """)

    public List<Object[]> getInspectioninfoForFarmerFromSaleDisposalOfDFls(String fruitsId);


    @Query(nativeQuery = true, value = """
         SELECT
         sadod.id,
         sadod.lot_number,
         sadod.number_of_dfls_disposed,
         sadod.rate_per100dfls_price ,
         sadod.race_id,
         rm.race_name,
         sadod.expected_date_of_hatching,
         sadod.date_of_disposal,
         sadod.source_of_dfls,
         sadod.name_and_address_of_the_farm
     FROM sale_and_disposal_of_dfls sadod
     LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
     WHERE sadod.fruits_id = :fruitsId
     AND sadod.active = 1
     AND sadod.is_verified = 1
     AND NOT EXISTS (
         SELECT 1 FROM crop_inspection ci
         WHERE ci.sale_and_disposal_id = sadod.id
         AND ci.active = 1
     );
    """)

    public List<Object[]> getInspectioninfoForFarmerFromSaleDisposalOfDFlsForNewApp(String fruitsId);

    /**
     * Returns sale-and-disposal-of-DFL rows that are
     *   1. already crop-inspected   (crop_inspection.is_crop_inspected = 1)
     *   2. NOT yet certified         (no row in fitness_certificate)
     * Used by the Fitness Certificate page to drive its DFL list.
     */
    @Query(nativeQuery = true, value = """
         SELECT
         sadod.id,
         sadod.lot_number,
         sadod.number_of_dfls_disposed,
         sadod.rate_per100dfls_price ,
         sadod.race_id,
         rm.race_name,
         sadod.expected_date_of_hatching,
         sadod.date_of_disposal,
         sadod.source_of_dfls,
         sadod.name_and_address_of_the_farm
     FROM sale_and_disposal_of_dfls sadod
     LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
     WHERE sadod.fruits_id = :fruitsId
     AND sadod.active = 1
     AND sadod.is_verified = 1
     AND EXISTS (
         SELECT 1 FROM crop_inspection ci
         WHERE ci.sale_and_disposal_id = sadod.id
         AND ci.active = 1
         AND ci.is_crop_inspected = 1
     )
     AND NOT EXISTS (
         SELECT 1 FROM fitness_certificate fc
         WHERE fc.sale_and_disposal_id = sadod.id
         AND fc.active = 1
     );
    """)

    public List<Object[]> getInspectioninfoForFarmerForFitnessCertificate(String fruitsId);


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
   AND sadod.active = 1
   AND sadod.is_sale_tracked = 0;
   """)
    public List<Object[]> getInspectioninfoForCocoonSaleTrack(String fruitsId);

    @Query(nativeQuery = true, value = """
         SELECT
             sadod.id,
             sadod.lot_number,
             sadod.number_of_dfls_disposed,
             sadod.rate_per100dfls_price,
             sadod.race_id,
             rm.race_name,
             sadod.expected_date_of_hatching,
             sadod.date_of_disposal,
             sadod.source_of_dfls,
             sadod.name_and_address_of_the_farm,
             sadod.fruits_id,
             gm.grainage_master_name,
             f.name_kan
         FROM sale_and_disposal_of_dfls sadod
         LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
         LEFT JOIN grainage_master gm ON sadod.grainage_id = gm.grainage_master_id
         LEFT JOIN farmer f ON sadod.fruits_id = f.fruits_id
         WHERE sadod.tsc = :tscMasterId
             AND sadod.active = 1
             AND sadod.is_verified = 0;
    """)

    public List<Object[]> getFarmerDetailsFromSaleDisposalOfDFlsByTsc(Long tscMasterId);


    @Query(nativeQuery = true, value = """
    SELECT
         sadod.id,
         sadod.lot_number,
         sadod.number_of_dfls_disposed,
         sadod.rate_per100dfls_price ,
         sadod.race_id,
         rm.race_name,
         sadod.name_and_address_of_the_farm,
         f.fruits_id
     FROM sale_and_disposal_of_dfls_rsso sadod
     LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
     LEFT JOIN farmer f ON sadod.fruits_id = f.fruits_id
     WHERE f.tsc_master_id = :tscMasterId
             AND sadod.active = 1
             AND f.active = 1
    """)
    public List<Object[]> getFarmerDetailsFromSaleDisposalOfDFlsRssoByTsc(Long tscMasterId);

    @Query(nativeQuery = true, value = """
     SELECT cm.chowki_id, cm.lot_numbers_crc, cm.lot_numbers_of_the_rsp, cm.numbers_of_dfls,
      cm.rate_per_100_dfls, cm.race_of_dfls, cm.source_of_dfls,cm.hatching_date, rm.race_name,f.fruits_id,f.first_name
      FROM chowki_management cm
      LEFT JOIN race_master rm ON cm.race_of_dfls = rm.race_id
    LEFT JOIN farmer f ON cm.fruits_id = f.fruits_id
    WHERE f.tsc_master_id = :tscMasterId
             AND f.active = 1
   """)
    public List<Object[]> getFarmerDetailsFromChowkiManagementByTsc(Long tscMasterId);

    @Query(nativeQuery = true, value = """
        SELECT
            sadod.lot_number,
            sadod.number_of_dfls_disposed,
            sadod.rate_per100dfls_price,
            rm.race_name,
            sadod.expected_date_of_hatching,
            sadod.date_of_disposal,
            sadod.source_of_dfls,
            sadod.name_and_address_of_the_farm,
            tm.name,
            f.first_name,
            f.father_name,
            f.fruits_id
        FROM sale_and_disposal_of_dfls sadod
        LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
        LEFT JOIN tsc_master tm ON tm.tsc_master_id = sadod.tsc
        LEFT JOIN farmer f ON f.fruits_id = sadod.fruits_id
        WHERE (:raceId IS NULL OR sadod.race_id = :raceId)
          AND (:tscId IS NULL OR sadod.tsc = :tscId)
          AND sadod.is_verified = 1
          AND sadod.active = 1
        """,
            countQuery = """
           SELECT COUNT(*)
            FROM sale_and_disposal_of_dfls sadod
            WHERE (:raceId IS NULL OR sadod.race_id = :raceId)
              AND (:tscId IS NULL OR sadod.tsc = :tscId)
              AND sadod.is_verified = 1
              AND sadod.active = 1
        """)
    Page<Object[]> getVerifiedDFLDetails(
            @Param("raceId") Long raceId,
            @Param("tscId") Long tscId,
            Pageable pageable);

    @Query(nativeQuery = true, value = """
     SELECT
            sadod.fruits_id,
            f.name_kan,
            rm.race_name,
            sadod.lot_number,
            sadod.number_of_dfls_disposed,
            sadod.rate_per100dfls_price
      FROM sale_and_disposal_of_dfls sadod
        LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
        LEFT JOIN farmer f ON f.fruits_id = sadod.fruits_id
        LEFT JOIN crop_inspection cp ON sadod.id = cp.sale_and_disposal_id
    WHERE (:tscMasterId IS NULL OR sadod.tsc = :tscMasterId)
        AND (cp.is_crop_inspected IS NULL OR cp.is_crop_inspected = 0)
        AND sadod.is_verified = 1
   """)
    List<Object[]> getPendingCropInspectionByTsc(Long tscMasterId);

    @Query(nativeQuery = true, value = """
     SELECT
            sadod.fruits_id,
            f.name_kan,
            rm.race_name,
            sadod.lot_number,
            sadod.number_of_dfls_disposed,
            sadod.rate_per100dfls_price
      FROM sale_and_disposal_of_dfls sadod
        LEFT JOIN race_master rm ON sadod.race_id = rm.race_id
        LEFT JOIN farmer f ON f.fruits_id = sadod.fruits_id
        LEFT JOIN fitness_certificate fc ON sadod.id = fc.sale_and_disposal_id
        LEFT JOIN crop_inspection cp ON sadod.id = cp.sale_and_disposal_id 
    WHERE (:tscMasterId IS NULL OR sadod.tsc = :tscMasterId)
        AND (fc.is_fc_issued IS NULL OR fc.is_fc_issued = 0)
        AND cp.is_crop_inspected = 1
   """)
    List<Object[]> getPendingFitnessCertificateByTsc(Long tscMasterId);
}
