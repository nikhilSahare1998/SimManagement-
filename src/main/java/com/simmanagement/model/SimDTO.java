package com.simmanagement.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SimDTO {

    private long simId;

    private long custId;

    private  int mobileNo;

    private String networkName;

    private LocalDate dtOfPurchasing;

}
