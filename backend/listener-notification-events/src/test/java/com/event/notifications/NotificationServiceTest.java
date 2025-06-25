package com.event.notifications;

import com.event.notifications.service.NotificationService;
import com.resume.common.library.model.notify.dto.NotificationEvent;
import com.resume.common.library.model.notify.enumerations.EventTypes;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private JavaMailSender mailSender;

    @Test
    void shouldSendEmailSuccessfully() {
        NotificationEvent event = new NotificationEvent(EventTypes.TEST_EVENT, "test@example.com", "Message");

        Mockito.doNothing().when(mailSender).send(Mockito.any(MimeMessage.class));

        Assertions.assertDoesNotThrow(() -> notificationService.sendEmail(event));
        Mockito.verify(mailSender, Mockito.times(1)).send(Mockito.any(MimeMessage.class));
    }
}
