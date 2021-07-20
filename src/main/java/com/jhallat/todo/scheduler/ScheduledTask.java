package com.jhallat.todo.scheduler;


public record ScheduledTask(int scheduleId,
                            long taskId,
                            String taskDescription,
                            int taskQuantity,
                            long goalId,
                            String goalDescription) {
}
