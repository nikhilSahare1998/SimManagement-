package com.simmanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Sim")
@Data
public class Sim implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sim_id")
    private Long sId;

    @Column(name = "network_Name")
    private String networkName;

    @Column(name = "dtOfPurchasing")
    private LocalDate dtOfPurchasing;

    @Column(name = "mobileNo.")
    private  int mobileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cust_Id")
    private Customer customer;


}
