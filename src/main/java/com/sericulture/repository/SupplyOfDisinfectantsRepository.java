package com.sericulture.repository;

import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.api.response.SupplyOfDisinfectantsResponse;
import com.sericulture.model.entity.FitnessCertificate;
import com.sericulture.model.entity.SupplyOfDisinfectants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SupplyOfDisinfectantsRepository extends JpaRepository<SupplyOfDisinfectants, Long> {

    public SupplyOfDisinfectants findBySupplyOfDisinfectantsIdAndUserMasterIdAndActive(long supplyOfDisinfectantsId,long userMasterId, boolean isActive);

//    @Query("SELECT new com.sericulture.model.api.SupplyOfDisinfectantsResponse(" +
//            "sod.supplyOfDisinfectantsId, " +
//            "sod.farmerId, " +
//            "sod.userMasterId, " +
//            "sod.disinfectantMasterId, " +
//            "sod.invoiceNoDate, " +
//            "sod.quantity, " +
//            "dm.disinfectantName, " +
//            "sod.quantitySupplied, " +
//            "sod.suppliedDate, " +
//            "um.tscName, " +
//            "dm.disinfectantMasterName, " +
//            "sod.sizeOfRearingHouse, " +
//            "sod.numbersOfDfls) " +
//            "FROM SupplyOfDisinfectants sod " +
//            "LEFT JOIN DisinfectantMaster dm ON dm.disinfectantMasterId = sod.disinfectantMasterId " +
//            "LEFT JOIN UserMaster um ON um.userMasterId = sod.userMasterId " +
//            "WHERE sod.userMasterId = :userMasterId " +
//            "ORDER BY sod.supplyOfDisinfectantsId DESC")
//  List<SupplyOfDisinfectantsResponse> getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(Long userMasterId);

//    @Query(value = "SELECT new com.sericulture.model.api.SupplyOfDisinfectantsResponse(" +
//            "sod.supply_of_disinfectants_id, " +
//            "sod.farmer_id, " +
//            "sod.user_master_id, " +
//            "sod.disinfectant_master_id, " +
//            "sod.invoice_no_date, " +
//            "sod.quantity, " +
//            "dm.disinfectant_name, " +
//            "sod.quantity_supplied, " +
//            "sod.supply_date, " +
//            "um.first_name, " +
//            "dm.disinfectant_master_name, " +
//            "sod.size_of_rearing_house, " +
//            "sod.no_of_dfls) " +
//            "FROM supply_of_disinfectants sod " +
//            "LEFT JOIN disinfectant_master dm ON dm.disinfectant_master_id = sod.disinfectant_master_id " +
//            "LEFT JOIN user_master um ON um.user_master_id = sod.user_master_id " +
//            "WHERE sod.user_master_id = :userMasterId " +
//            "ORDER BY sod.supply_of_disinfectants_id DESC", nativeQuery = true)
//    List<SupplyOfDisinfectantsResponse> getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(@Param("userMasterId") Long userMasterId);

    @Query(nativeQuery = true, value = """
    SELECT
        sod.supply_of_disinfectants_id,
        sod.farmer_id,
        sod.user_master_id,
        sod.disinfectant_master_id,
        sod.invoice_no_date,
        sod.quantity,
        sod.disinfectant_name,
        sod.quantity_supplied,
        sod.supply_date,
        sod.size_of_rearing_house,
        sod.no_of_dfls,
        um.first_name,
        dm.disinfectant_master_name
    FROM
        supply_of_disinfectants sod
    LEFT JOIN user_master um ON um.user_master_id = sod.user_master_id
    LEFT JOIN disinfectant_master dm ON dm.disinfectant_master_id = sod.disinfectant_master_id
    WHERE sod.user_master_id = :userMasterId
    ORDER BY sod.supply_of_disinfectants_id DESC
""")
    List<Object[]> getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(@Param("userMasterId") Long userMasterId);



    Optional<SupplyOfDisinfectantsResponse> findBySupplyOfDisinfectantsIdAndUserMasterId(Long supplyOfDisinfectantsId, Long userMasterId);

}
