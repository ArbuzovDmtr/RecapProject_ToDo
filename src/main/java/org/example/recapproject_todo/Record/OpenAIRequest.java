package org.example.recapproject_todo.Record;

import java.util.List;

public record OpenAIRequest(
        String model,
        List<Message> messages
) {}