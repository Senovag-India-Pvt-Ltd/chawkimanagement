package com.sericulture.repository;

import com.sericulture.model.entity.CropInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CropInspectionRepository extends JpaRepository<CropInspection, Long> {

    @Query(nativeQuery = true, value = """
  SELECT
    ci.date,
    ci.note,
    cs.name AS crop_status_name,
    m.name AS mount_name,
    r.name As reason_name
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


}
