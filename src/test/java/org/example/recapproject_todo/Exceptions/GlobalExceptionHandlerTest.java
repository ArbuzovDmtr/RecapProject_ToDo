package org.example.recapproject_todo.Exceptions;

import org.example.recapproject_todo.Controller.TodoController;
import org.example.recapproject_todo.Services.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;





@WebMvcTest(TodoController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Test
    void shouldReturn404_whenTodoNotFound() throws Exception {
        when(todoService.getTodoById("999"))
                .thenThrow(new NotFoundException("Todo with id: 999 not found."));

        mockMvc.perform(get("/api/todo/999"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Todo with id: 999 not found."));
    }
}
