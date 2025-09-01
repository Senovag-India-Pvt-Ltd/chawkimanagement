package com.sericulture.model.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmerMulberryExtensionResponse {
    private int serialNumber;
    private String firstName;
    private String fatherName;
    private String fruitsId;
    private String scheme;
    private String addressText;
    private String tscName;
    private String tscNameKannada;
    private String mulberryVarietyName;
    private String mulberryVarietyNameKannada;
    private String plantationDate;
    private String numberOfSaplings;
    private String mulberryArea;
    private String spacing;
    private String applicationType;
    private String uprootingReason;
    private String uprootingDate;
}
