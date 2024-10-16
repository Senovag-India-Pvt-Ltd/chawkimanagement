package com.sericulture.repository;

import com.sericulture.model.entity.CropInspection;
import com.sericulture.model.entity.FarmerMulberryExtension;
import com.sericulture.model.entity.FitnessCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerMulberryExtensionRepository extends JpaRepository<FarmerMulberryExtension, Long> {

    public FarmerMulberryExtension findByFarmerMulberryExtensionIdAndActive(long id, boolean isActive);
}
