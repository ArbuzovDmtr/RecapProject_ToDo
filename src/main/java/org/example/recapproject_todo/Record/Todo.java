package org.example.recapproject_todo.Record;


import org.example.recapproject_todo.Status.TodoStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("todos")
public record Todo(@Id String id, String description, TodoStatus status) {
}
