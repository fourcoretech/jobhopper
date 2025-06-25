package com.resume.common.library.model.notify.dto;

import com.resume.common.library.model.notify.enumerations.EventTypes;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {
    private EventTypes eventType;
    private String recipientEmail;
    private String summary;
}
