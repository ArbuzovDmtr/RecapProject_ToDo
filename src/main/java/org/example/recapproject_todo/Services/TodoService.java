package org.example.recapproject_todo.Services;

import org.example.recapproject_todo.DTO.PostTodoDTO;
import org.example.recapproject_todo.DTO.PutTodoDTO;
import org.example.recapproject_todo.Record.Todo;
import org.example.recapproject_todo.Repository.TodoRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final IdService idService;

    public TodoService(TodoRepo todoRepo, IdService idService) {
        this.todoRepo = todoRepo;
        this.idService = idService;
    }


    public List<Todo> getAllTodos() {
        return todoRepo.findAll();
    }


    public Todo createTodo(PostTodoDTO dto) {
        Todo todo = new Todo(idService.randomId(), dto.description(), dto.status());
        return todoRepo.save(todo);
    }

    public Todo getTodoById(String id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));}

    public Todo updateTodo (String id, PutTodoDTO dto) {
        Todo  existingTodo = getTodoById(id);

        Todo  updatedTodo  = new Todo (
                existingTodo.id(),
                dto.description(),
                dto.status()
        );
        return todoRepo.save(updatedTodo);}


    public void deleteTodoById(String id) {
        if (!todoRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todoRepo.deleteById(id);
    }
}