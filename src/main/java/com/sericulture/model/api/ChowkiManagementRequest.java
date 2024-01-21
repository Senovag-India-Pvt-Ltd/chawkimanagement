package com.sericulture.model.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChowkiManagementRequest {


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
