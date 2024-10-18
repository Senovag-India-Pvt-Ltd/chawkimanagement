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
    private  Long chowkiId;
    private  Long farmerId;
    private  Long cropInspectionTypeId;
    private LocalDate date;
    private Long reasonId;
    private String note;
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
//    @Schema(name = "error", example = "true")
    private Boolean error;

//    @Schema(name = "error_description", example = "Username or password is incorrect")
    private String error_description;
}
