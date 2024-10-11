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
public class DisinfectantMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disinfectant_master_seq")
    @SequenceGenerator(name = "disinfectant_master_seq", sequenceName = "disinfectant_master_seq", allocationSize = 1)
    @Column(name = "disinfectant_master_id")
    private Long disinfectantMasterId;


    @Size(min = 2, max = 250, message = "Disinfectant name should be more than 1 characters.")
    @Column(name = "disinfectant_master_name", unique = true)
    private String disinfectantMasterName;

    @Column(name = "disinfectant_master_name_in_kannada", unique = true)
    private String disinfectantMasterNameInKannada;
}
