package com.sericulture.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FarmerMulberryExtension extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmer_mulberry_extension_seq")
    @SequenceGenerator(name = "farmer_mulberry_extension_seq", sequenceName = "farmer_mulberry_extension_seq", allocationSize = 1)

    @Column(name = "farmer_mulberry_extension_id")
    private Long farmerMulberryExtensionId;

    @Column(name = "farmer_land_details_id")
    private Long farmerLandDetailsId;

    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(name = "mulberry_area")
    private String mulberryArea;

    @Column(name = "mulberry_variety_id")
    private Long mulberryVarietyId;

    @Column(name = "photopath")
    private String photoPath;

    @Column(name = "extension_date")
    private LocalDate extensionDate;

    @Column(name = "spacing")
    private String spacing;

    @Column(name = "scheme")
    private String scheme;

    @Column(name = "application_type")
    private String applicationType;

    @Column(name = "uprooting_date")
    private LocalDate uprootingDate;

    @Column(name = "uprooting_reason")
    private String uprootingReason;

    @Column(name = "number_of_sapplings")
    private String noOfSapplings;

}
