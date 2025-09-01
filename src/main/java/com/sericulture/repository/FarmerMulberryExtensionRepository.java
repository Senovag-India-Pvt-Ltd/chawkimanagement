package com.sericulture.repository;

import com.sericulture.model.entity.CropInspection;
import com.sericulture.model.entity.FarmerMulberryExtension;
import com.sericulture.model.entity.FitnessCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FarmerMulberryExtensionRepository extends JpaRepository<FarmerMulberryExtension, Long> {

    public FarmerMulberryExtension findByFarmerMulberryExtensionIdAndActive(long id, boolean isActive);

    @Query(nativeQuery = true, value = """
            SELECT
                F.name_kan AS first_name,
                F.father_name,
                F.fruits_id,
                fm.scheme,
                fa.address_text,
                tm.name AS tsc,
                tm.name_in_kannada,
                mv.mulberry_variety_name,
                mv.mulberry_variety_name_in_kannada,
                fm.extension_date AS plantation_date,
                fm.number_of_sapplings,
                fm.mulberry_area,
                fm.spacing,
                fm.application_type,
                fm.uprooting_reason,
                fm.uprooting_date
            FROM FARMER F
            INNER JOIN farmer_address fa
                ON F.FARMER_ID = fa.FARMER_ID
               AND fa.active = 1
            INNER JOIN farmer_land_details fl
                ON F.FARMER_ID = fl.farmer_id
               AND fa.FARMER_ID = fl.farmer_id
               AND fl.active = 1
            INNER JOIN farmer_mulberry_extension fm
                ON F.FARMER_ID = fm.farmer_id
               AND fm.farmer_id = fa.FARMER_ID
               AND fm.farmer_land_details_id = fl.farmer_land_details_id
               AND fm.active = 1
            INNER JOIN mulberry_variety mv
                ON fm.mulberry_variety_id = mv.mulberry_variety_id
               AND mv.active = 1
            LEFT JOIN tsc_master tm
                ON F.tsc_master_id = tm.tsc_master_id
               AND tm.active = 1
            WHERE F.active = 1
              AND (:tscId IS NULL OR tm.tsc_master_id = :tscId)
              AND (:applicationType IS NULL OR fm.application_type = :applicationType);
        """,
            countQuery = """
           SELECT COUNT(*)
            FROM FARMER F
            INNER JOIN farmer_address fa
            ON F.FARMER_ID = fa.FARMER_ID
            AND fa.active = 1
            INNER JOIN farmer_land_details fl
            ON F.FARMER_ID = fl.farmer_id
            AND fa.FARMER_ID = fl.farmer_id
            AND fl.active = 1
            INNER JOIN farmer_mulberry_extension fm
            ON F.FARMER_ID = fm.farmer_id
            AND fm.farmer_id = fa.FARMER_ID
            AND fm.farmer_land_details_id = fl.farmer_land_details_id
            AND fm.active = 1
            INNER JOIN mulberry_variety mv
            ON fm.mulberry_variety_id = mv.mulberry_variety_id
            AND mv.active = 1
            LEFT JOIN tsc_master tm
            ON F.tsc_master_id = tm.tsc_master_id
            AND tm.active = 1
            WHERE F.active = 1
            AND (:tscId IS NULL OR tm.tsc_master_id = :tscId)
            AND (:applicationType IS NULL OR fm.application_type = :applicationType);
        """)
    Page<Object[]> getFarmerMulberryExtensionDetails(
            @Param("tscId") Long tscId,
            @Param("applicationType") String applicationType,
            Pageable pageable);
}
