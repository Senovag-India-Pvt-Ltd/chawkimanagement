package com.sericulture.model.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOfDisinfectantResponse {
    private int serialNumber;
    private String farmerName;
    private String fatherName;
    private String fruitsId;
    private String invoiceNoDate;
    private Integer quantity;
    private String disinfectantName;
    private Integer quantitySupplied;
    private String supplyDate;
    private String sizeOfRearingHouse;
    private Integer noOfDfls;
    private String disinfectantMasterName;
    private String tscName;
}
