package com.resume.common.library.model.openai.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionDTO {

    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String systemFingerprint;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public String getSystemFingerprint() {
        return systemFingerprint;
    }

    public void setSystemFingerprint(String systemFingerprint) {
        this.systemFingerprint = systemFingerprint;
    }

    // Nested Classes
    public static class Choice {
        private int index;
        private Messages messages;
        private Object logprobs; // Assuming null or a specific object
        private String finishReason;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Messages getMessage() {
            return messages;
        }

        public void setMessage(Messages messages) {
            this.messages = messages;
        }

        public Object getLogprobs() {
            return logprobs;
        }

        public void setLogprobs(Object logprobs) {
            this.logprobs = logprobs;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }
    }

    public static class Messages {
        private String role;
        private String content;
        private Object refusal; // Assuming null or a specific object

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getRefusal() {
            return refusal;
        }

        public void setRefusal(Object refusal) {
            this.refusal = refusal;
        }
    }

    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
        private Map<String, Integer> promptTokensDetails;
        private Map<String, Integer> completionTokensDetails;

        public int getPromptTokens() {
            return promptTokens;
        }

        public void setPromptTokens(int promptTokens) {
            this.promptTokens = promptTokens;
        }

        public int getCompletionTokens() {
            return completionTokens;
        }

        public void setCompletionTokens(int completionTokens) {
            this.completionTokens = completionTokens;
        }

        public int getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(int totalTokens) {
            this.totalTokens = totalTokens;
        }

        public Map<String, Integer> getPromptTokensDetails() {
            return promptTokensDetails;
        }

        public void setPromptTokensDetails(Map<String, Integer> promptTokensDetails) {
            this.promptTokensDetails = promptTokensDetails;
        }

        public Map<String, Integer> getCompletionTokensDetails() {
            return completionTokensDetails;
        }

        public void setCompletionTokensDetails(Map<String, Integer> completionTokensDetails) {
            this.completionTokensDetails = completionTokensDetails;
        }
    }
}
