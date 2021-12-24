package com.simmanagement.repository;

import com.simmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM  customer WHERE  DATE_ADD(date_of_birth,INTERVAL YEAR(CURDATE())-YEAR(date_of_birth)+ IF(DAYOFYEAR(CURDATE()) > DAYOFYEAR(date_of_birth),1,0)YEAR)BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)", nativeQuery = true)
    List<Customer> getCustomer();


    @Query(value = "SELECT * FROM  customer WHERE  DATE_ADD(date_of_birth,INTERVAL YEAR(CURDATE())-YEAR(date_of_birth)+ IF(DAYOFYEAR(CURDATE()) > DAYOFYEAR(date_of_birth),1,0)YEAR)BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)", nativeQuery = true)
    List<Customer> getCustomersListOneDayBeforeBirthday();
}
