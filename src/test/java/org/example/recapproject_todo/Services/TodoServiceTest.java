package org.example.recapproject_todo.Services;

import org.example.recapproject_todo.DTO.PostTodoDTO;
import org.example.recapproject_todo.Record.Todo;
import org.example.recapproject_todo.Repository.TodoRepo;
import org.example.recapproject_todo.Status.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    IdService idService;
    @Mock
    TodoRepo todoRepo;
    @InjectMocks
    private TodoService todoService;


    @BeforeEach
    void setUp() {
        todoService = new TodoService(todoRepo, idService);}


    @Test
    void getAllTodos_shouldReturnAllTodos() {
        // given
        List<Todo> expected = List.of(
                new Todo("1", "Test1", TodoStatus.OPEN),
                new Todo("2", "Test2", TodoStatus.DONE)
        );

        when(todoRepo.findAll()).thenReturn(expected);

        // when
        List<Todo> actual = todoService.getAllTodos();

        // then
        assertEquals(expected, actual);
        verify(todoRepo, times(1)).findAll();
        verifyNoMoreInteractions(todoRepo, idService);
    }

    @Test
    void createTodo_shouldSaveAndReturnTodo() {
        // given
        PostTodoDTO dto = new PostTodoDTO("Test1", TodoStatus.IN_PROGRESS);
        Todo expected = new Todo("abc-123", "Test1", TodoStatus.IN_PROGRESS);

        when(idService.randomId()).thenReturn("abc-123");
        when(todoRepo.save(expected)).thenReturn(expected);

        // when
        Todo actual = todoService.createTodo(dto);

        // then
        assertEquals(expected, actual);
        verify(idService, times(1)).randomId();
        verify(todoRepo, times(1)).save(expected);
        verifyNoMoreInteractions(todoRepo, idService);
    }
}