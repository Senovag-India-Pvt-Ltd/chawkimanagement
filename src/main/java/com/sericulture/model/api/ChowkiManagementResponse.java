package com.sericulture.model.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ChowkiManagementResponse {

    private int chowkiId;

    private String farmerName;

    private String fatherName;

    private String fruitsId;

    private String dflsSource;

    private String raceName;

    private Long numbersOfDfls;

    private String lotNumberRsp;

    private String lotNumberCrc;

    private String villageName;

    private String districtName;

    private String stateName;

    private String talukName;

    private String hobliName;

    private Integer tsc;

    private String soldAfter1stOr2ndMould;

    private Float ratePer100Dfls;

    private Float price;

    private Date hatchingDate;

    private Date dispatchDate;

}
