package com.ordering.procurementFlow.controllers;

import com.ordering.procurementFlow.DTO.EmailRequest;
import com.ordering.procurementFlow.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/send")
    public ResponseEntity<EmailRequest> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            String to = emailRequest.getToEmail();
            String subject = emailRequest.getSubject();
            String body = emailRequest.getBody();
            emailService.sendEmail(to, subject, body);
            return ResponseEntity.ok(emailRequest);
        } catch (MessagingException e) {
            // Log the exception or return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailRequest);
        }
    }
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello from secured endpoint");
    }
}
