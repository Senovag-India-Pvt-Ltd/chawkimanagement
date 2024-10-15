package com.sericulture.model.api.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class CropInspectionRequest {
     private  Long chowkiId;
     private  Long farmerId;
     private  Long cropInspectionTypeId;
     private LocalDate date;
     private Long reasonId;
     private String note;
     private Long cropStatusId;
     private Long mountId;
     private Float expectedCocoon;
     private String lotTestDetails;
     private Long diseaseStatusId;
     private Float noOfChandies;
     private LocalDate expectedMarkerDate;
     private Long farmerLandDetailsId;
     private Long mulberryVarietyId;
     private LocalDate extensionDate;
     private String mulberryArea;
     private String photoPath;
     private String spacing;
     private String scheme;
     private String applicationType;
     private LocalDate uprootingDate;
     private String uprootingReason;
     private String noOfSapplings;
     private String fitnessCertificatePath;
     private String cropInspectionPath;
}
