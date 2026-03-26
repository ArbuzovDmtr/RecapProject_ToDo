package org.example.recapproject_todo.Services;

import org.example.recapproject_todo.DTO.PostTodoDTO;
import org.example.recapproject_todo.DTO.PutTodoDTO;
import org.example.recapproject_todo.Exceptions.NotFoundException;
import org.example.recapproject_todo.Record.Todo;
import org.example.recapproject_todo.Repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final IdService idService;
    private final OpenAIService gptService;

    public TodoService(TodoRepo todoRepo, IdService idService,OpenAIService gptService) {
        this.todoRepo = todoRepo;
        this.idService = idService;
        this.gptService= gptService;
    }


    public List<Todo> getAllTodos() {
        return todoRepo.findAll();
    }


    public Todo createTodo(PostTodoDTO dto) {
        String corrected = gptService.grammarTest(dto.description());
        Todo todo = new Todo(idService.randomId(), corrected, dto.status());
        return todoRepo.save(todo);
    }

    public Todo getTodoById(String id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo with id: " +  id + " not found." ));}

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
            throw new NotFoundException("Todo with id: " +  id + " not found.");
        }
        todoRepo.deleteById(id);
    }
}