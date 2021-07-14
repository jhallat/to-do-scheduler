package com.jhallat.todo.scheduler.batch;

public record BatchResponse(boolean success, int updated, int added) {
}
