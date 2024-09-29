package com.sericulture.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
public class CropInspection extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crop_inspection_seq")
    @SequenceGenerator(name = "crop_inspection_seq", sequenceName = "crop_inspection_seq", allocationSize = 1)

    @Column(name = "crop_inspection_id")
    private Long cropInspectionId;

    @Column(name = "chowki_id ")
    private Long chowkiId;

    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(name = "crop_inspection_type_id")
    private Long cropInspectionTypeId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reason_id")
    private Long reasonId;

    @Column(name = "note")
    private String note;

    @Column(name = "crop_status_id")
    private Long cropStatusId;

    @Column(name = "mount_id")
    private Long mountId;

}
