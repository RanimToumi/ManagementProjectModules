package com.ordering.procurementFlow.services;

import com.ordering.procurementFlow.Models.Email;
import com.ordering.procurementFlow.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {
    @Autowired
    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true indicates HTML content
        javaMailSender.send(message);
        // Save the sent email to the database for future reference
        Email email = new Email();
        email.setToEmail(to);
        email.setSubject(subject);
        email.setBody(body);
        emailRepository.save(email);
    }
}
