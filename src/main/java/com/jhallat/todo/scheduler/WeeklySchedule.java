package com.jhallat.todo.scheduler;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeeklySchedule(@JsonProperty("id") int id,
                             @JsonProperty("description") String description,
                             @JsonProperty("selectedDays") DaySelection selectedDays) {
}
