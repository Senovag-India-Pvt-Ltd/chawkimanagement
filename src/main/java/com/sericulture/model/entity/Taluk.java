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
public class Taluk implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TALUK_SEQ")
    @SequenceGenerator(name = "TALUK_SEQ", sequenceName = "TALUK_SEQ", allocationSize = 1)
    @Column(name = "TALUK_ID")
    private Long talukId;

    @Size(min = 2, max = 250, message = "TALUK name should be more than 1 characters.")
    @Column(name = "TALUK_NAME", unique = true)
    private String talukName;
}