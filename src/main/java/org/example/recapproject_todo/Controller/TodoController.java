package org.example.recapproject_todo.Controller;


import org.example.recapproject_todo.DTO.PostTodoDTO;
import org.example.recapproject_todo.DTO.PutTodoDTO;
import org.example.recapproject_todo.Record.Todo;
import org.example.recapproject_todo.Services.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todo")
public class TodoController {

    private final TodoService todoService;
    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    @GetMapping()
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping()
    public Todo createTodo(@RequestBody PostTodoDTO dto) {

        return todoService.createTodo(dto);}

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoService.getTodoById(id);}

    @PutMapping("/{id}")
    public Todo putTodo(@PathVariable String id, @RequestBody PutTodoDTO dto) {
        return todoService.updateTodo(id, dto);
    }
}
