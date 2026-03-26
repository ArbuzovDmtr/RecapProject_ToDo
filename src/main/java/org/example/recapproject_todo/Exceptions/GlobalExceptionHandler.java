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
                .errorMessage(exception.getReason())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleGenericException(Exception exception) {
        return ErrorMessage.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage("An unexpected error occurred.")
                .build();
    }
}
