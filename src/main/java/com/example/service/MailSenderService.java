package com.example.service;

import com.example.repository.EmailHistoryRepository;
import com.example.util.JWTUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${server.url}")
    private String serverUrl;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    void sendMimeEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromEmail);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
            Thread.sleep(2000);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailVerification(String toAccount, String name, Integer id) {
        String jwt = JWTUtil.encodeEmailJwt(id);
        String url = serverUrl + "/api/v1/auth/verification/email/" + jwt;

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<h1 style=\"text-align: center\">Hello %s</h1>", name));
        builder.append("<p>");
        builder.append(String.format("<a href=\"%s\"> Click link to complete registration </a>", url));
        builder.append("</p>");

        //  --> with  thread by help of method
        executorService.submit(() -> {
            sendMimeEmail(toAccount, "Kun uz registration compilation", builder.toString());
        });
    }

}
