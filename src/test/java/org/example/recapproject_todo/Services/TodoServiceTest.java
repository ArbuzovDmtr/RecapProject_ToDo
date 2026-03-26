package org.example.recapproject_todo.Services;

import org.example.recapproject_todo.DTO.PostTodoDTO;
import org.example.recapproject_todo.DTO.PutTodoDTO;
import org.example.recapproject_todo.Exceptions.NotFoundException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    IdService idService;
    @Mock
    OpenAIService gptService;
    @Mock
    TodoRepo todoRepo;
    @InjectMocks
    private TodoService todoService;


    @BeforeEach
    void setUp() {
        todoService = new TodoService(todoRepo, idService,gptService);}


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

    @Test
    void getTodoById_shouldReturnTodo_whenTodoExists() {
        // given
        Todo expected = new Todo("1", "Test todo", TodoStatus.OPEN);
        when(todoRepo.findById("1")).thenReturn(Optional.of(expected));

        // when
        Todo actual = todoService.getTodoById("1");

        // then
        assertEquals(expected, actual);
        verify(todoRepo, times(1)).findById("1");
        verifyNoMoreInteractions(todoRepo, idService);
    }

    @Test
    void getTodoById_shouldThrow_whenTodoDoesNotExist() {
        // given
        when(todoRepo.findById("999")).thenReturn(Optional.empty());

        // when
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> todoService.getTodoById("999")
        );

        // then
        assertEquals(404, exception.getStatusCode().value());
        assertEquals("Todo with id: 999 not found.", exception.getReason());
        verify(todoRepo, times(1)).findById("999");
        verifyNoMoreInteractions(todoRepo, idService);
    }

    @Test
    void updateTodo_shouldUpdateAndReturnTodo_whenTodoExists() {
        // given
        Todo existingTodo = new Todo("1", "Old description", TodoStatus.OPEN);
        PutTodoDTO dto = new PutTodoDTO("New description", TodoStatus.DONE);
        Todo updatedTodo = new Todo("1", "New description", TodoStatus.DONE);

        when(todoRepo.findById("1")).thenReturn(Optional.of(existingTodo));
        when(todoRepo.save(updatedTodo)).thenReturn(updatedTodo);

        // when
        Todo actual = todoService.updateTodo("1", dto);

        // then
        assertEquals(updatedTodo, actual);
        verify(todoRepo, times(1)).findById("1");
        verify(todoRepo, times(1)).save(updatedTodo);
        verifyNoMoreInteractions(todoRepo, idService);
    }

    @Test
    void updateTodo_shouldThrow_whenTodoDoesNotExist() {
        // given
        PutTodoDTO dto = new PutTodoDTO("New description", TodoStatus.DONE);
        when(todoRepo.findById("999")).thenReturn(Optional.empty());

        // when
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> todoService.updateTodo("999", dto)
        );

        // then
        assertEquals(404, exception.getStatusCode().value());
        assertEquals("Todo with id: 999 not found.", exception.getReason());
        verify(todoRepo, times(1)).findById("999");
        verifyNoMoreInteractions(todoRepo, idService);
    }

    @Test
    void deleteTodoById_shouldDeleteTodo_whenTodoExists() {
        // given
        when(todoRepo.existsById("1")).thenReturn(true);

        // when
        todoService.deleteTodoById("1");

        // then
        verify(todoRepo, times(1)).existsById("1");
        verify(todoRepo, times(1)).deleteById("1");
        verifyNoMoreInteractions(todoRepo, idService);
    }

    @Test
    void deleteTodoById_shouldThrow_whenTodoDoesNotExist() {
        // given
        when(todoRepo.existsById("999")).thenReturn(false);

        // when
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> todoService.deleteTodoById("999")
        );

        // then
        assertEquals(404, exception.getStatusCode().value());
        assertEquals("Todo with id: 999 not found.", exception.getReason());
        verify(todoRepo, times(1)).existsById("999");
        verifyNoMoreInteractions(todoRepo, idService);
    }
}