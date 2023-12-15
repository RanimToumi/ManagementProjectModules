package com.Marketing.MarketingAPI.services;
import com.Marketing.MarketingAPI.models.Email;
import com.Marketing.MarketingAPI.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {
    @Autowired
    private final EmailRepository emailRepository;
    @Autowired
    private final JavaMailSender javaMailSender;
    public void sendEmail(String to, Long campaignId, String subject, String body) throws MessagingException {
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
        email.setBody("http://localhost:8080/api/questionnaire/campaign/"+String.valueOf(campaignId)+" "+ body);
        emailRepository.save(email);
    }
    public void sendEmailToPeople(List<String> toMails,Long campaignId ,String subject, String body)throws MessagingException{
        for (String to:toMails){
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body+" : http://localhost:4200/questionnaire/"+String.valueOf(campaignId)+" ", true); // true indicates HTML content
            javaMailSender.send(message);
            // Save the sent email to the database for future reference
            Email email = new Email();
            email.setToEmail(to);
            email.setSubject(subject);
            email.setBody(body+" : http://localhost:4200/api/questionnaire/campaign/"+String.valueOf(campaignId));
            emailRepository.save(email);
        }
    }
}
