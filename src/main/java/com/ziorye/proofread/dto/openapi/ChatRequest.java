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
        this.messages.add(new Message("system", "You will be provided with a sentence in %s, and your task is to translate it into %s.".formatted(sourceLanguage, targetLanguage)));
        this.messages.add(new Message("user", prompt));
    }
}
