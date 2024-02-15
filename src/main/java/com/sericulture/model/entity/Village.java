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
public class Village implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VILLAGE_SEQ")
    @SequenceGenerator(name = "VILLAGE_SEQ", sequenceName = "VILLAGE_SEQ", allocationSize = 1)
    @Column(name = "VILLAGE_ID")
    private Long villageId;

    @Size(min = 2, max = 250, message = "VILLAGE name should be more than 1 characters.")
    @Column(name = "VILLAGE_NAME", unique = true)
    private String villageName;
}