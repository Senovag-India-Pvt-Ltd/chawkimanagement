package com.sericulture.model.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CropInspectionResponse {
    private  Long cropInspectionId;
    private  int serialNumber;
    private  Long chowkiId;
    private  Long farmerId;
    private  Long cropInspectionTypeId;
    private LocalDate date;
    private Long reasonId;
    private String note;
    private String tscName;
    private Long cropStatusId;
    private Long mountId;
    private String cropDate;
    private String cropStatusName;
    private String mountName;
    private String reasonName;
    private Float expectedCocoon;
    private String lotTestDetails;
    private Long diseaseStatusId;
    private Long farmerLandDetailsId;
    private String mulberryArea;
    private Long mulberryVarietyId;
    private LocalDate extensionDate;
    private String photoPath;
    private Float noOfChandies;
    private String cropInspectionTypeName;
    private String applicationType;
    private LocalDate uprootingDate;
    private String uprootingReason;
    private String noOfSapplings;
    private Long fitnessCertificateId;
    private String fitnessCertificatePath;
    private Long saleAndDisposalId;
    private String dflsSource;
    private String numbersOfDfls;
    private String lotNumberRsp;
    private Long raceOfDfls;
    private String raceName;
    private String farmerName;
    private String fatherName;
    private String fruitsId;
    private String cropInspectionDate;
//    @Schema(name = "error", example = "true")
    private Boolean error;

//    @Schema(name = "error_description", example = "Username or password is incorrect")
    private String error_description;
}
