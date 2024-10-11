package com.sericulture.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplyOfDisinfectants extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supply_of_disinfectants_seq")
    @SequenceGenerator(name = "supply_of_disinfectants_seq", sequenceName = "supply_of_disinfectants_seq", allocationSize = 1)

    @Column(name = "supply_of_disinfectants_id")
    private Long supplyOfDisinfectantsId;

    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(name = "user_master_id")
    private Long userMasterId;

    @Column(name = "disinfectant_master_id")
    private Long disinfectantMasterId;

    @Column(name = "invoice_no_date")
    private String invoiceNoDate;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "disinfectant_name")
    private String disinfectantName;

    @Column(name = "quantity_supplied")
    private Long quantitySupplied;

    @Column(name = "supply_date")
    private LocalDate suppliedDate;

    @Column(name = "size_of_rearing_house")
    private String sizeOfRearingHouse;

    @Column(name = "no_of_dfls")
    private Long numbersOfDfls;

}
