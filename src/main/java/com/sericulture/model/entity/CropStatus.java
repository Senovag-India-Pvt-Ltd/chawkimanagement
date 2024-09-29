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
public class CropStatus extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crop_status_seq")
    @SequenceGenerator(name = "crop_status_seq", sequenceName = "crop_status_seq", allocationSize = 1)
    @Column(name = "crop_status_id")
    private Long cropStatusId;


    @Size(min = 2, max = 250, message = "Mount name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String name;
}
