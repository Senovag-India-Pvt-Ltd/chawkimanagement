package com.sericulture.model.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TrackCocoonResponse {
    private Long trackCocoonId;
    private LocalDate marketAuctionDate;
    private Long marketMasterId;
    private Long cocoonsQty;
    private Long ratePerKg;
    private String buyerType;
    private Long reelerId;
    private Long chowkiId;
    private String externalUnitRegistrationName;
}
