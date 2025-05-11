package com.resume.common.library.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GPTPrompt {
    private String model;
    private List<Messages> messages;
}
