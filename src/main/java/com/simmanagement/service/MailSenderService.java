package com.simmanagement.service;

import com.simmanagement.controller.CustomerController;
import com.simmanagement.model.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service("mailServices")
public class MailSenderService {

    @Autowired
    JavaMailSender mailSender;
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            logger.info("Sending mails...");
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            logger.info("Mails sent.");
            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            logger.error("Mails not sent..");
            e.printStackTrace();
        }
    }


}
