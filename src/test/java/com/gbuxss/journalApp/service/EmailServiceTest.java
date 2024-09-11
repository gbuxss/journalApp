package com.gbuxss.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {

    private final EmailService emailService;

    @Autowired
    public EmailServiceTest(EmailService emailService) {
        this.emailService = emailService;
    }

    @Test
    @Disabled
    void testEmail() {

        emailService.sendEmailForSA("arshdeep@freezeion.com", "hello", "hello man how are you doing!!");
    }
}
