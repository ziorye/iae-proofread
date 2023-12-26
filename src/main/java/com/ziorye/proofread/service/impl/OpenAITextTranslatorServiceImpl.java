package com.ziorye.proofread.service.impl;

import com.ziorye.proofread.dto.openapi.ChatRequest;
import com.ziorye.proofread.dto.openapi.ChatResponse;
import com.ziorye.proofread.service.TextTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAITextTranslatorServiceImpl implements TextTranslatorService {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Override
    public String translateText(String text, String sourceLanguageCode, String targetLanguageCode) {
        ChatRequest request = new ChatRequest(model, text, sourceLanguageCode, targetLanguageCode);

        ChatResponse response = restTemplate.postForObject(
                apiUrl,
                request,
                ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
