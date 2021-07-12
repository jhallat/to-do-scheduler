package com.jhallat.todo.scheduler;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DaySelection(@JsonProperty("sunday") boolean sunday,
                           @JsonProperty("monday") boolean monday,
                           @JsonProperty("tuesday") boolean tuesday,
                           @JsonProperty("wednesday") boolean wednesday,
                           @JsonProperty("thursday") boolean thursday,
                           @JsonProperty("friday") boolean friday,
                           @JsonProperty("saturday") boolean saturday) {
}
