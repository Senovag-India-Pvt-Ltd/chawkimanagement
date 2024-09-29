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
public class Reason extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reason_seq")
    @SequenceGenerator(name = "reason_seq", sequenceName = "reason_seq", allocationSize = 1)
    @Column(name = "reason_id")
    private Long reasonId;


    @Size(min = 2, max = 250, message = "Mount name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String name;
}
