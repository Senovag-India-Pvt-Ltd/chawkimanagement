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
public class DiseaseStatus extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disease_status_seq")
    @SequenceGenerator(name = "disease_status_seq", sequenceName = "disease_status_seq", allocationSize = 1)
    @Column(name = "disease_status_id")
    private Long diseaseStatusId;


    @Size(min = 2, max = 250, message = "Disease Status name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description", unique = true)
    private String description;
}
