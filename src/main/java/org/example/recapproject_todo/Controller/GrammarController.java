package org.example.recapproject_todo.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

public class GrammarController {

    private final RestClient restClient;

    public GrammarController(@Value("${app.openai-api-key}") String openaiApiKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + openaiApiKey)
                .build();
    }


}
