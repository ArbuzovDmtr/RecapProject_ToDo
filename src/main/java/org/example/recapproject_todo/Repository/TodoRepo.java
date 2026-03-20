package org.example.recapproject_todo.Repository;

import org.example.recapproject_todo.Record.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepo extends MongoRepository<Todo,String> {
}
