package com.jhallat.todo.scheduler;

public record ScheduleForDay(long taskId,
                             String description,
                             boolean quantifiable,
                             String scheduleType,
                             int quantity,
                             int goalId,
                             String goalDescription,
                             int weeklyMax,
                             boolean weeklyMaxReached) {
}
