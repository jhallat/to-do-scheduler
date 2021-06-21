package com.jhallat.todo.scheduler;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ToDoTimestamp(@JsonProperty("timestamp") String timestamp) {
}
