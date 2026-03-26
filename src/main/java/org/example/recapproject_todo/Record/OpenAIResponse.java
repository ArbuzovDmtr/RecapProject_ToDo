package org.example.recapproject_todo.Record;

import java.util.List;

public record OpenAIResponse(List<Choice> choices
) {}