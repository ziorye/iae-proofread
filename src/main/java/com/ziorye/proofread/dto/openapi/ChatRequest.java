package com.ziorye.proofread.dto.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatRequest {
    private String model;
    private List<Message> messages;

    public ChatRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }

    public ChatRequest(String model, String prompt, String sourceLanguage, String targetLanguage) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "You will be provided with a content in %s, and your task is to translate it into %s. The code does not need to be translated. If it is in markdown format, the markdown format output must be retained after translation.".formatted(sourceLanguage, targetLanguage)));
        this.messages.add(new Message("user", prompt));
    }
}
