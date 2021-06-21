package com.jhallat.todo.scheduler;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ToDo(@JsonProperty("id") long id,
                   @JsonProperty("timestamp") String timestamp,
                   @JsonProperty("description") String description,
                   @JsonProperty("completed") boolean completed) {}




