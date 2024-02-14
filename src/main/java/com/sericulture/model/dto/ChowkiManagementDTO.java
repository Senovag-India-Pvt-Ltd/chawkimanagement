package com.sericulture.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ChowkiManagementDTO {

    private int chowkiId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Farmer Name must contain only letters and numbers")
    @Schema(name = "farmerName", example = "string")
    private String farmerName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Father Name must contain only letters and numbers")
    @Schema(name = "fatherName", example = "string")
    private String fatherName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Fruits Id must contain only letters and numbers")
    @Schema(name = "fruitsId", example = "string")
    private String fruitsId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DFLS Source must contain only letters and numbers")
    @Schema(name = "dflsSource", example = "string")
    private String dflsSource;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Race of DFLS must contain only letters and numbers")
    @Schema(name = "raceOfDfls", example = "string")
    private String raceOfDfls;

    private Long numbersOfDfls;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Lot Number RSP must contain only letters and numbers")
    @Schema(name = "lotNumberRsp", example = "string")
    private String lotNumberRsp;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Lot Number CRC must contain only letters and numbers")
    @Schema(name = "lotNumberCrc", example = "string")
    private String lotNumberCrc;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Village must contain only letters and numbers")
    @Schema(name = "village", example = "string")
    private String village;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "District must contain only letters and numbers")
    @Schema(name = "district", example = "string")
    private String district;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "State must contain only letters and numbers")
    @Schema(name = "state", example = "string")
    private String state;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "TSC must contain only letters and numbers")
    @Schema(name = "tsc", example = "string")
    private String tsc;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sold After 1st or 2nd Mould must contain only letters and numbers")
    @Schema(name = "soldAfter1stOr2ndMould", example = "string")
    private String soldAfter1stOr2ndMould;

    private Float ratePer100Dfls;

    private Float price;

    private Date dispatchDate;




}
