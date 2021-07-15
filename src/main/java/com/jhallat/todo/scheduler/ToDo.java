package com.jhallat.todo.scheduler;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ToDo(@JsonProperty("id") long id,
                   @JsonProperty("activeDate") String activeDate,
                   @JsonProperty("createdDate") String createdDate,
                   @JsonProperty("completionDate") String completionDate,
                   @JsonProperty("description") String description,
                   @JsonProperty("completed") boolean completed,
                   @JsonProperty("taskId") long taskId,
                   @JsonProperty("quantity") int quantity) {}




