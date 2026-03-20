package org.example.recapproject_todo.Record;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("todos")
public record Todo(@Id String id, String description, String status) {
}
