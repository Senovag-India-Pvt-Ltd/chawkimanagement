package com.sericulture.model.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FitnessCertificateResponse {
    private int serialNumber;
    private String farmerName;
    private String fatherName;
    private String fruitsId;
    private Long fitnessCertificateId;
    private String fitnessCertificatePath;
    private Long farmerId;
    private Double ratePer100DflsPrice;
    private Float ratePer100Dfls;
    private Integer numberOfDflsDisposed;
    private String lotNumber;
    private String raceName;
    private String tscName;
}
