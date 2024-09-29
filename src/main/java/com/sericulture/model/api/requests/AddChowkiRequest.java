package com.sericulture.model.api.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddChowkiRequest {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Farmer Name must contain only letters and numbers")
    @Schema(name = "farmerName", example = "string")
    private String farmerName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Father Name must contain only letters and numbers")
    @Schema(name = "fatherName", example = "string")
    private String fatherName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Fruits Id must contain only letters and numbers")
    @Schema(name = "fruitsId", example = "string", required = true)
    private String fruitsId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DFLS Source must contain only letters and numbers")
    @Schema(name = "dflsSource", example = "string")
    private String dflsSource;

    private Integer raceOfDfls;

    private Long numbersOfDfls;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Lot Number RSP must contain only letters and numbers")
    @Schema(name = "lotNumberRsp", example = "string")
    private String lotNumberRsp;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Lot Number CRC must contain only letters and numbers")
    @Schema(name = "lotNumberCrc", example = "string")
    private String lotNumberCrc;

    private Integer village;

    private Integer district;

    private Integer state;

    private Integer tsc;
    private Integer hobli;
    private Integer taluk;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sold After 1st or 2nd Mould must contain only letters and numbers")
    @Schema(name = "soldAfter1stOr2ndMould", example = "string")
    private String soldAfter1stOr2ndMould;

    private Float ratePer100Dfls;

    private Float price;

    private Date dispatchDate;

    private Date hatchingDate;

    private Long farmerId;

    private int isVerified;

}
