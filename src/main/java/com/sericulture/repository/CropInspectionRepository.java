package com.sericulture.repository;

import com.sericulture.model.entity.CropInspection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CropInspectionRepository extends JpaRepository<CropInspection, Long> {

    public CropInspection findByCropInspectionIdAndActive(long id, boolean isActive);

//    Optional<CropInspection> findByFarmerIdAndSaleAndDisposalIdAndActive(Long farmerId, Long saleAndDisposalId, Boolean active);

    @Query("SELECT ci FROM CropInspection ci WHERE ci.farmerId = :farmerId AND ci.saleAndDisposalId = :saleAndDisposalId AND ci.active = true")
    Optional<CropInspection> findByFarmerIdAndSaleAndDisposalIdAndActive(
            @Param("farmerId") Long farmerId,
            @Param("saleAndDisposalId") Long saleAndDisposalId
    );




    @Query(nativeQuery = true, value = """
  SELECT
    ci.date,
    ci.note,
    cs.name AS crop_status_name,
    m.name AS mount_name,
    r.name As reason_name,
    ci.chowki_id
FROM
    crop_inspection ci
Left JOIN
    crop_status cs ON ci.crop_status_id = cs.crop_status_id
Left JOIN
    mount m ON ci.mount_id = m.mount_id
Left JOIN
    reason r ON ci.reason_id = r.reason_id
  WHERE
      ci.chowki_id = :chowkiId
""")
    public List<Object[]> getInspectionDetails(@Param("chowkiId") Long chowkiId);

    @Query(nativeQuery = true, value = """
  SELECT
    ci.date,
    ci.note,
    cs.name AS crop_status_name,
    m.name AS mount_name,
    r.name As reason_name,
    ci.sale_and_disposal_id
FROM
    crop_inspection ci
Left JOIN
    crop_status cs ON ci.crop_status_id = cs.crop_status_id
Left JOIN
    mount m ON ci.mount_id = m.mount_id
Left JOIN
    reason r ON ci.reason_id = r.reason_id
  WHERE
      ci.sale_and_disposal_id = :saleAndDisposalId
      AND ci.is_crop_inspected = 1
""")
    public List<Object[]> getInspectionDetailsForSaleAndDisposalDFL(@Param("saleAndDisposalId") Long saleAndDisposalId);


    @Query(nativeQuery = true, value = """
    SELECT cit.crop_inspection_type_id, cit.name
    FROM crop_inspection_type cit
    WHERE cit.active = 1
        And cit.crop_inspection_type_id NOT IN (
            SELECT ci.crop_inspection_type_id
            FROM crop_inspection ci
            WHERE ci.chowki_id = :chowkiId
    )
    """)
    List<Object[]> getInspectionTypeForCrop(@Param("chowkiId") Long chowkiId);


    @Query(nativeQuery = true, value = """
    SELECT cit.crop_inspection_type_id, cit.name
    FROM crop_inspection_type cit
    WHERE cit.active = 1
        And cit.crop_inspection_type_id NOT IN (
            SELECT ci.crop_inspection_type_id
            FROM crop_inspection ci
            WHERE ci.sale_and_disposal_id = :saleAndDisposalId
    )
    """)
    List<Object[]> getInspectionTypeForCropFromSaleAndDisposalOfDfl(@Param("saleAndDisposalId") Long saleAndDisposalId);

    @Query(nativeQuery = true, value = """
        SELECT
            ci.date,
            ci.note,
            cs.name AS crop_status_name,
            m.name AS mount_name,
            r.name As reason_name,
            ci.sale_and_disposal_id,
            tm.name,
            f.first_name,
            f.father_name,
            f.fruits_id,
            ci.crop_inspection_id
        FROM
            crop_inspection ci
        Left JOIN
            crop_status cs ON ci.crop_status_id = cs.crop_status_id
        Left JOIN
            farmer f ON ci.farmer_id = f.farmer_id
        Left JOIN
            sale_and_disposal_of_dfls sd ON sd.id = ci.sale_and_disposal_id
        Left JOIN
            mount m ON ci.mount_id = m.mount_id
        Left JOIN
            reason r ON ci.reason_id = r.reason_id
        Left JOIN
            tsc_master tm ON tm.tsc_master_id = sd.tsc
          WHERE
              (:tscId IS NULL OR sd.tsc = :tscId)
              AND ci.is_crop_inspected = 1
              AND ci.active = 1
        """,
            countQuery = """
           SELECT COUNT(*)
              FROM crop_inspection ci
              LEFT JOIN crop_status cs ON ci.crop_status_id = cs.crop_status_id
              Left JOIN farmer f ON ci.farmer_id = f.farmer_id
              LEFT JOIN sale_and_disposal_of_dfls sd ON sd.id = ci.sale_and_disposal_id
              LEFT JOIN mount m ON ci.mount_id = m.mount_id
              LEFT JOIN reason r ON ci.reason_id = r.reason_id
              LEFT JOIN tsc_master tm ON tm.tsc_master_id = sd.tsc
              WHERE
                  (:tscId IS NULL OR sd.tsc = :tscId)
                  AND ci.is_crop_inspected = 1
                  AND ci.active = 1;
        """)
    Page<Object[]> getCropInspectionDetails(
            @Param("tscId") Long tscId,
            Pageable pageable);

    public CropInspection findByCropInspectionIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);

    @Query(nativeQuery = true, value = """
            SELECT
                ci.crop_inspection_id,
                ci.crop_inspection_path,
                ci.farmer_id,
                ci.date,
                ci.note,
                cs.name AS crop_status_name,
                m.name AS mount_name,
                r.name AS reason_name,
                sadod.rate_per100dfls_price,
                sadod.number_of_dfls_disposed,
                sadod.lot_number,
                sadod.race_id,
                sadod.expected_date_of_hatching,
                rm.race_name,
                gm.grainage_master_name,
                ci.fruits_id,
                ci.sale_and_disposal_id,
                ci.crop_inspection_type_id,
                sadod.is_disposed
                FROM crop_inspection ci
                LEFT JOIN crop_status cs ON ci.crop_status_id = cs.crop_status_id
                LEFT JOIN mount m ON ci.mount_id = m.mount_id
                LEFT JOIN reason r ON ci.reason_id = r.reason_id
                LEFT JOIN sale_and_disposal_of_dfls sadod ON sadod.id = ci.sale_and_disposal_id AND sadod.active = 1
                LEFT JOIN race_master rm ON rm.race_id = sadod.race_id AND rm.active = 1
                LEFT JOIN grainage_master gm ON gm.grainage_master_id = sadod.grainage_id AND gm.active = 1
                WHERE ci.farmer_id = :farmerId
                AND ci.is_crop_inspected = 1
                AND ci.active = 1
                ORDER BY ci.created_date DESC;
    """)
    public List<Object[]> getCropInspectionPath(@Param("farmerId") Long farmerId);


}
