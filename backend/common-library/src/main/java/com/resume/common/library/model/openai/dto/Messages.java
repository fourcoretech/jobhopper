package com.resume.common.library.model.openai.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    private String role;
    private String content;
}
