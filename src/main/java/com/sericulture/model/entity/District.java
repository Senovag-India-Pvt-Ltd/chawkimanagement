package com.sericulture.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class District implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISTRICT_SEQ")
    @SequenceGenerator(name = "DISTRICT_SEQ", sequenceName = "DISTRICT_SEQ", allocationSize = 1)
    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Size(min = 2, max = 250, message = "DISTRICT name should be more than 1 characters.")
    @Column(name = "DISTRICT_NAME", unique = true)
    private String districtName;
}