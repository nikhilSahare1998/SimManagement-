package com.simmanagement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Setter
@Getter
public class CustomerSimDTO {


    private Long custId;


    private String fullName;

    private String emailId;

    private String address;

    private String mobileNo;

    private LocalDate dateOfBirth;
}
