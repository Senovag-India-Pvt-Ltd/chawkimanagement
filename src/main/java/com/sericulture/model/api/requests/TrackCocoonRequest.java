package com.sericulture.model.api.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TrackCocoonRequest {
    private Long trackCocoonId;
    private LocalDate marketAuctionDate;
    private Long marketMasterId;
    private Long cocoonsQty;
    private Long ratePerKg;
    private String buyerType;
    private Long reelerId;
    private Integer chowkiId;
    private Integer saleAndDisposalId;
    private String externalUnitRegistrationName;
}
