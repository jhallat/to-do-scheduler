package com.jhallat.todo.scheduler;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ToDoActiveDate(@JsonProperty("activeDate") String activeDate) {
}
