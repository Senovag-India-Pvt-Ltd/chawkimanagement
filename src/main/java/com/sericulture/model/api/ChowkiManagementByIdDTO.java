package com.sericulture.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ChowkiManagementByIdDTO {

    private int chowkiId;

    private String farmerName;

    private String fatherName;

    private String fruitsId;

    private String dflsSource;

    private Integer raceOfDfls;

    private String raceName;

    private Long numbersOfDfls;

    private String lotNumberRsp;

    private String lotNumberCrc;

    private String villageName;

    private String districtName;

    private String stateName;

    private String talukName;

    private String hobliName;

    private String tscName;

    private Integer village;

    private Integer district;

    private Integer state;

    private Integer taluk;

    private Integer hobli;

    private Integer tsc;

    private String soldAfter1stOr2ndMould;

    private Float ratePer100Dfls;

    private Float price;

    private Date hatchingDate;

    private Date dispatchDate;
    private Long farmerId;
    private int isVerified;
    private String receiptNo;
}
