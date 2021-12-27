package com.simmanagement.controller;


import com.simmanagement.model.CustomerDTO;
import com.simmanagement.model.CustomerSimDTO;
import java.io.IOException;
import com.simmanagement.model.SimDTO;
import com.simmanagement.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @PostMapping("/customer")
    public String saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.save(customerDTO);
    }

    @PostMapping("/saveSim")
    public String saveSim(@RequestBody List<SimDTO> sim) {
        return customerService.createSim(sim);
    }

    @GetMapping("/getSims/{custId}")
    public List<SimDTO> getSims(@PathVariable Long custId) {
        return customerService.getSims(custId);
    }

    @GetMapping("/downloadExcelFile")
    public void downloadExcelFile(HttpServletResponse response) throws IOException {
        ByteArrayInputStream byteArrayInputStream = customerService.dailyExport();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=CustomerBirthdayList.xlsx");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }

    @GetMapping("/getAllSim")
    public List<SimDTO> getAllSims() {
        return customerService.getAllSim();
    }

}
