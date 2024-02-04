package com.sericulture.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ChowkiManagementDTO {

    private int chowkiId;

    private String farmerName;

    private String fatherName;

    private String fruitsId;

    private String dflsSource;

    private String raceOfDfls;

    private Long numbersOfDfls;

    private String lotNumberRsp;

    private String lotNumberCrc;

    private String village;

    private String district;

    private String state;

    private String tsc;

    private String soldAfter1stOr2ndMould;

    private Float ratePer100Dfls;

    private Float price;

    private Date dispatchDate;



}
