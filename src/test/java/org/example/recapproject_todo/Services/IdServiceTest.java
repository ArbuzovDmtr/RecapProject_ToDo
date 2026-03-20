package org.example.recapproject_todo.Services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdServiceTest {

    private final IdService idService = new IdService();

    @Test
    void randomId_shouldReturnNonNullString() {
        String id = idService.randomId();

        assertNotNull(id);
        assertFalse(id.isBlank());
    }

    @Test
    void randomId_shouldReturnDifferentValues() {
        String firstId = idService.randomId();
        String secondId = idService.randomId();

        assertNotEquals(firstId, secondId);
    }

}