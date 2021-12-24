package com.simmanagement.model;

import com.simmanagement.entity.Sim;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CustomerDTO {


    private Long custId;


    private String fullName;

    private String emailId;


    private String address;


    private LocalDate dateOfBirth;

    private List<Sim> simCards;
}
