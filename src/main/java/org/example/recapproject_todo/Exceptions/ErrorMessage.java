package org.example.recapproject_todo.Exceptions;

import lombok.Builder;

@Builder
public record ErrorMessage(int errorCode, String errorMessage) {
}
