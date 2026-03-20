package org.example.recapproject_todo.Services;

import org.example.recapproject_todo.DTO.PostTodoDTO;
import org.example.recapproject_todo.DTO.PutTodoDTO;
import org.example.recapproject_todo.Record.Todo;
import org.example.recapproject_todo.Repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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


    public Todo createTodo(PostTodoDTO dto){
        return new Todo(idService.randomId(), dto.description(), dto.status());}

    public Todo getTodoById(String id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Todo with id " + id + " not found"));}

    public Todo updateTodo (String id, PutTodoDTO dto) {
        Todo  existingTodo = getTodoById(id);

        Todo  updatedTodo  = new Todo (
                existingTodo.id(),
                dto.description(),
                dto.status()
        );

        return todoRepo.save(updatedTodo);
    }
}