package com.sericulture.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackCocoon extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "track_cocoon_seq")
    @SequenceGenerator(name = "track_cocoon_seq", sequenceName = "track_cocoon_seq", allocationSize = 1)

    @Column(name = "track_cocoon_id")
    private Long trackCocoonId;

    @Column(name = "market_auction_date")
    private LocalDate marketAuctionDate;

    @Column(name = "market_master_id")
    private Long marketMasterId;

    @Column(name = "cocoons_qty")
    private Long cocoonsQty;

    @Column(name = "rate_per_kg")
    private Long ratePerKg;

    @Column(name = "buyer_type")
    private String buyerType;

    @Column(name = "reeler_id")
    private Long reelerId;

    @Column(name = "chowki_id")
    private Long chowkiId;

    @Column(name = "external_unit_registration_name")
    private String externalUnitRegistrationName;

}
