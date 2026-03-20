package org.example.recapproject_todo.Services;

import org.example.recapproject_todo.Repository.TodoRepo;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepo todoRepo;

    public TodoService(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;

    }
    }
