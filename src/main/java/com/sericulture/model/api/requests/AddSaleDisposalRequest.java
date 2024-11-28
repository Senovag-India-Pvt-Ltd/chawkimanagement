package com.sericulture.model.api.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class AddSaleDisposalRequest {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Fruits Id must contain only letters and numbers")
    @Schema(name = "fruitsId", example = "string", required = true)
    private String fruitsId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DFLS Source must contain only letters and numbers")
    @Schema(name = "dflsSource", example = "string")
    private String dflsSource;

    private Integer raceId;

    private Long numberOfDflsDisposed;

    private String lotNumber;
    private String receiptNo;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sold After 1st or 2nd Mould must contain only letters and numbers")
    @Schema(name = "soldAfter1stOr2ndMould", example = "string")
    private String soldAfter1stOr2ndMould;

    private Double ratePer100DflsPrice;

    private LocalDate dateOfDisposal;

    private Float price;

    private Long tsc;

    private LocalDate expectedDateOfHatching;

    private Long farmerId;
    private int isSaleTracked;
    private int isVerified;

}
