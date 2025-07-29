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
public class UserMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_master_seq")
    @SequenceGenerator(name = "user_master_seq", sequenceName = "user_master_seq", allocationSize = 1)
    @Column(name = "user_master_id")
    private Long userMasterId;

    @Column(name = "tsc_master_id")
    private Long tscMasterId;

    @Column(name = "first_name")
    private String tscName;

}
