package com.simmanagement.service;


import com.simmanagement.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    @Autowired
    MailSenderService mailService;

    @Autowired
    CustomerService customerService;



    @Scheduled(cron = "0 0 0 * * *")
    public void sevenDayBeforeResponse() {
        Mail mail = new Mail();
        mail.setMailFrom("emailId");
        mail.setMailTo(customerService.getMailAfterSevenDay());
        mail.setMailTo(mail.getMailTo());
        mail.setMailSubject("Subject");
        mail.setMailContent("Massage!!!\n\nThanks\nSim Management");
        mailService.sendEmail(mail);
    }

}
