package com.simmanagement.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name ="Customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cust_id")
    private Long cId;

    @Column(name="fullName")
    private String fullName;

    @Column(name = "email")
    private String emailId;

    @Column(name = "address")
    private String address;

    @Column(name = "dateOfBirth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

   /* @Column(name = "mobileNo.")
    private  int mobileNo;*/

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<Sim> simCards;


}
