package com.aig.backend.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.OpenAiClient;
import org.springframework.ai.request.CompletionRequest;
import org.springframework.ai.response.CompletionResponse;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final OpenAiClient openAiClient;

    public AiController(OpenAiClient openAiClient) {
        this.openAiClient = openAiClient;
    }

    @PostMapping("/generate")
    public String generateText(@RequestBody String prompt) {
        CompletionRequest request = new CompletionRequest.Builder()
                .model("text-davinci-003")
                .prompt(prompt)
                .maxTokens(100)
                .build();

        CompletionResponse response = openAiClient.complete(request);
        return response.getChoices().get(0).getText();
    }
}
