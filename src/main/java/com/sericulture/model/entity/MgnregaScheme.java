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
public class MgnregaScheme extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mgnrega_scheme_seq")
    @SequenceGenerator(name = "mgnrega_scheme_seq", sequenceName = "mgnrega_scheme_seq", allocationSize = 1)

    @Column(name = "mgnrega_scheme_id")
    private Long mgnregaSchemeId;

    @Column(name = "acres_planted")
    private String acresPlanted;

    @Column(name = "spacing_followed_feet")
    private String spacingFollwedFeet;

    @Column(name = "spacing_procured_nos")
    private String spacingProcuredNos;

    @Column(name = "spacing_followed")
    private String spacingFollowed;

    @Column(name = "spacing_procured")
    private String spacingProcured;

    @Column(name = "no_of_cutting_planted")
    private Long noOfCuttingPlanted;

    @Column(name = "no_of_successfull_samplings")
    private Long noOfSuccessfullSamplings;

}
