package org.example.recapproject_todo.DTO;

import org.example.recapproject_todo.Status.TodoStatus;

public record PostTodoDTO(String description, TodoStatus status) {
}


