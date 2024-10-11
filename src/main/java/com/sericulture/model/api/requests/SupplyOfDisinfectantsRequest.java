package com.sericulture.model.api.requests;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class SupplyOfDisinfectantsRequest {
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
