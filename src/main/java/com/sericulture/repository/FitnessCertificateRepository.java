package com.sericulture.repository;

import com.sericulture.model.api.response.CropInspectionResponse;
import com.sericulture.model.api.response.SupplyOfDisinfectantsResponse;
import com.sericulture.model.entity.CropInspection;
import com.sericulture.model.entity.FitnessCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FitnessCertificateRepository extends JpaRepository<FitnessCertificate, Long> {

    public FitnessCertificate findByFitnessCertificateIdAndActive(long id, boolean isActive);

//    Optional<FitnessCertificate> findByFarmerIdAndSaleAndDisposalIdAndActive(Long farmerId, Long saleAndDisposalId, Boolean active);

    @Query("SELECT fc FROM FitnessCertificate fc WHERE fc.farmerId = :farmerId AND fc.saleAndDisposalId = :saleAndDisposalId AND fc.active = true")
    Optional<FitnessCertificate> findByFarmerIdAndSaleAndDisposalIdAndActive(
            @Param("farmerId") Long farmerId,
            @Param("saleAndDisposalId") Long saleAndDisposalId
    );



    @Query(nativeQuery = true, value = """
            SELECT
                fc.fitness_certificate_id,
                fc.fitness_certificate_path,
                fc.farmer_id,
                sadod.rate_per100dfls_price,
                sadod.number_of_dfls_disposed,
                sadod.lot_number,
                sadod.race_id,
                rm.race_name
                FROM fitness_certificate fc
                Inner JOIN sale_and_disposal_of_dfls sadod ON sadod.id = fc.sale_and_disposal_id  AND (sadod.is_disposed = 0 OR sadod.is_disposed IS NULL) AND  sadod.active = 1
                LEFT JOIN race_master rm ON rm.race_id = sadod.race_id AND rm.active = 1
                 WHERE fc.farmer_id =:farmerId
                 AND fc.is_fc_issued = 1
                 AND fc.active = 1
                 ORDER BY fc.created_date DESC;
    """)

    public List<Object[]> getFitnessCertificatePath(Long farmerId);
}
