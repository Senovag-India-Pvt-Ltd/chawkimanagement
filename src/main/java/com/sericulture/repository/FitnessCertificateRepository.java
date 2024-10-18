package com.sericulture.repository;

import com.sericulture.model.api.response.CropInspectionResponse;
import com.sericulture.model.api.response.SupplyOfDisinfectantsResponse;
import com.sericulture.model.entity.CropInspection;
import com.sericulture.model.entity.FitnessCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FitnessCertificateRepository extends JpaRepository<FitnessCertificate, Long> {

    public FitnessCertificate findByFitnessCertificateIdAndActive(long id, boolean isActive);

    @Query(nativeQuery = true, value = """
    SELECT fc.fitness_certificate_id,fc.fitness_certificate_path,fc.farmer_id
        FROM fitness_certificate fc
        WHERE fc.farmer_id =:farmerId
    """)

    public List<Object[]> getFitnessCertificatePath(Long farmerId);
}
