package org.example.recapproject_todo.Services;

import org.example.recapproject_todo.Record.Message;
import org.example.recapproject_todo.Record.OpenAIRequest;
import org.example.recapproject_todo.Record.OpenAIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
@Service
public class OpenAIService {

    private final RestClient restClient;

    public OpenAIService(@Value("${app.openai-api-key}") String openaiApiKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + openaiApiKey)
                .build();
    }

    public String grammarTest (String message){
        OpenAIRequest request = new OpenAIRequest(
                "gpt-5.4-mini",
                List.of(new Message("user",
                        "Überprüfe die Rechtschreibung, " +
                                "Füge nur den korrigierten Text hinzu, ohne weitere Zeichen." +
                                "Überprüfe, ob das Geschriebene in einer anderen Tastaturbelegung einen sinnvollen Satz ergibt." +
                                "Wenn die Schreibweise korrekt ist, füge ihn unverändert ein.:\n" + message))
        );
        OpenAIResponse response = restClient.post()
                .body(request)
                .retrieve()
                .body(OpenAIResponse.class);

        return response.choices().getFirst().message().content();
    }
}
