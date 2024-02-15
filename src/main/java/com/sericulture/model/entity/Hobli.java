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
public class Hobli implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOBLI_SEQ")
    @SequenceGenerator(name = "HOBLI_SEQ", sequenceName = "HOBLI_SEQ", allocationSize = 1)
    @Column(name = "HOBLI_ID")
    private Long hobliId;

    @Size(min = 2, max = 250, message = "HOBLI name should be more than 1 characters.")
    @Column(name = "HOBLI_NAME", unique = true)
    private String hobliName;
}