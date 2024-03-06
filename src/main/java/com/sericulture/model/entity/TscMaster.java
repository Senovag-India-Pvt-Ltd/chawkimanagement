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
public class TscMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tsc_master_seq")
    @SequenceGenerator(name = "tsc_master_seq", sequenceName = "tsc_master_seq", allocationSize = 1)
    @Column(name = "tsc_master_id")
    private Long tscMasterId;


    @Size(min = 2, max = 250, message = "Tsc Name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String name;
}
