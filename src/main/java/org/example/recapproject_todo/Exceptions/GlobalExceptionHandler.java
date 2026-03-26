package org.example.recapproject_todo.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleTodoNotFound(NotFoundException exception){
        System.out.println(exception.getMessage());
        return ErrorMessage.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(exception.getMessage())
                .build();
    }
}
