package org.example.recapproject_todo.DTO;

import org.example.recapproject_todo.Status.TodoStatus;

public record PutTodoDTO(String description, TodoStatus status) {
}
