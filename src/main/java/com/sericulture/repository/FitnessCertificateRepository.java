package com.sericulture.repository;

import com.sericulture.model.entity.CropInspection;
import com.sericulture.model.entity.FitnessCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FitnessCertificateRepository extends JpaRepository<FitnessCertificate, Long> {

    public FitnessCertificate findByFitnessCertificateIdAndActive(long id, boolean isActive);
}
