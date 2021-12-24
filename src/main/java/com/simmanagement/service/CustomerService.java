package com.simmanagement.service;

import com.simmanagement.controller.CustomerController;
import com.simmanagement.entity.Customer;
import com.simmanagement.entity.Sim;
import com.simmanagement.model.CustomerDTO;
import com.simmanagement.model.CustomerSimDTO;
import com.simmanagement.model.SimDTO;
import com.simmanagement.repository.CustomerRepository;
import com.simmanagement.repository.SimRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {


    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SimRepository simRepository;

    public String save(CustomerDTO customerDTO) {
        logger.info("Saving the customer");
        Customer customer = new Customer();
        customer.setCId(customerDTO.getCustId());
        customer.setAddress(customerDTO.getAddress());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setFullName(customerDTO.getFullName());
        customer.setEmailId(customerDTO.getEmailId());

        customerRepository.save(customer);
        logger.info("customer saved successfully");
        return "Customer Saved Successfully";
    }


    public String createSim(List<SimDTO> sim) {
        logger.info("Saving the Sim");
        List<Sim> sims = new ArrayList<>();
        for (SimDTO simDTO : sim) {
            Sim simEntity = new Sim();
            simEntity.setCustomer(customerRepository.findById(simDTO.getCustId()).get());
            simEntity.setDtOfPurchasing(simDTO.getDtOfPurchasing());
            simEntity.setMobileNo(simDTO.getMobileNo());
            simEntity.setNetworkName(simDTO.getNetworkName());
            sims.add(simEntity);
        }
        simRepository.saveAll(sims);
        logger.info("Sim saved successfully");
        return "Sim Save Successfully";
    }

    public List<SimDTO> getAllSim() {
        logger.info("Fetching Sims..... ");
        List<SimDTO> simDTOS = new ArrayList<>();
        List<Sim> all = simRepository.findAll();
        for (Sim s : all) {
            SimDTO simDTO = new SimDTO();
            simDTO.setSimId(s.getSId());
            simDTO.setNetworkName(s.getNetworkName());
            simDTO.setMobileNo((s.getMobileNo()));
            simDTO.setDtOfPurchasing(s.getDtOfPurchasing());
            simDTO.setCustId(s.getCustomer().getCId());
            simDTOS.add(simDTO);
        }
        return simDTOS;
    }

    public List<SimDTO> getSims(Long custId) {
        Customer customer = customerRepository.findById(custId).get();
        List<Sim> simCards = customer.getSimCards();
        List<SimDTO> simDTOS = new ArrayList<>();
        logger.info("Fetching the sim using customer id ...");
        for (Sim s : simCards) {
            SimDTO simDTO = new SimDTO();
            simDTO.setSimId(s.getSId());
            simDTO.setNetworkName(s.getNetworkName());
            simDTO.setDtOfPurchasing(s.getDtOfPurchasing());
            simDTO.setMobileNo(s.getMobileNo());
            simDTO.setCustId(s.getCustomer().getCId());
            simDTOS.add(simDTO);
        }
        return simDTOS;
    }


    public String[] getMailAfterSevenDay() {
        return customerRepository.getCustomer().stream().map(e->e.getEmailId()).collect(Collectors.toList()).toArray(String[]::new);

    }

    public ByteArrayInputStream dailyExport() {
        List<Customer> collect = customerRepository.getCustomersListOneDayBeforeBirthday();
        logger.info("dailyExport Method executed...");
        List<CustomerSimDTO> customerSimDTOS = new ArrayList<>();
        for (Customer c : collect) {
            List<Integer> mobileNums = new ArrayList<>();
            for (Sim sim : c.getSimCards()) {
                mobileNums.add(sim.getMobileNo());
            }
            String mobileNum = mobileNums.stream().map(String::valueOf).collect(Collectors.joining(","));
            CustomerSimDTO customerSimDTO = new CustomerSimDTO();
            customerSimDTO.setCustId(c.getCId());
            customerSimDTO.setAddress(c.getAddress());
            customerSimDTO.setFullName(c.getFullName());
            customerSimDTO.setEmailId(c.getEmailId());
            customerSimDTO.setDateOfBirth(c.getDateOfBirth());
            customerSimDTO.setMobileNo(mobileNum);
            customerSimDTOS.add(customerSimDTO);
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Daily Export");

            Row row = sheet.createRow(0);

            // Define header cell style
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Creating header cells
            Cell cell = row.createCell(0);
            cell.setCellValue("Customer Id");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Full Name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Email");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Address");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Mobile Number");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Date Of Birth");
            cell.setCellStyle(headerCellStyle);

            // Creating data rows
            for (int i = 0; i < customerSimDTOS.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(customerSimDTOS.get(i).getCustId());
                dataRow.createCell(1).setCellValue(customerSimDTOS.get(i).getFullName());
                dataRow.createCell(2).setCellValue(customerSimDTOS.get(i).getEmailId());
                dataRow.createCell(3).setCellValue(customerSimDTOS.get(i).getAddress());
                dataRow.createCell(4).setCellValue(customerSimDTOS.get(i).getMobileNo());
                dataRow.createCell(5).setCellValue(customerSimDTOS.get(i).getDateOfBirth().toString());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            logger.error("Error during export Excel file", ex);
            return null;
        }

    }

}



