package com.sericulture.model.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
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
}
