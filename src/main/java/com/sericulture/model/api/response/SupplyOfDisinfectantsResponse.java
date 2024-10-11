package com.sericulture.model.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class SupplyOfDisinfectantsResponse {
    private Long supplyOfDisinfectantsId;
    private Long farmerId;
    private Long userMasterId;
    private Long disinfectantMasterId;
    private String invoiceNoDate;
    private Long quantity;
    private String disinfectantName;
    private Long quantitySupplied;
    private LocalDate suppliedDate;
    private String sizeOfRearingHouse;
    private Long numbersOfDfls;
}
