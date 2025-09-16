package com.myCompany.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;


@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;


    @Disabled
    @Test
    void MailTest(){
        emailService.sendEmail("rajshiromanisinghkatiyar@gmail.com" , "Testing Email Service via Java code" , "Hello there,Wassup??");
    }
}
