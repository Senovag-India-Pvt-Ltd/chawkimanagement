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
public class CropInspectionType extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crop_inspection_type_seq")
    @SequenceGenerator(name = "crop_inspection_type_seq", sequenceName = "crop_inspection_type_seq", allocationSize = 1)
    @Column(name = "crop_inspection_type_id")
    private Long cropInspectionTypeId;


    @Size(min = 2, max = 250, message = "Crop Inspection Type name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String name;
}
