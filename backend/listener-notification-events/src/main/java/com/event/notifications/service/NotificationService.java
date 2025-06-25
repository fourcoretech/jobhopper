package com.event.notifications.service;

import com.event.notifications.exception.NotificationException;
import com.resume.common.library.model.notify.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    public void sendEmail(NotificationEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getRecipientEmail());
            message.setSubject("New notification!");
            message.setText("Here is your notification summary: \n\n" + event.getSummary());
            mailSender.send(message);
        } catch (Exception e) {
            throw new NotificationException(String.format("Failed to send email. %s", e.getMessage()), HttpStatusCode.valueOf(500));
        }
    }
}
