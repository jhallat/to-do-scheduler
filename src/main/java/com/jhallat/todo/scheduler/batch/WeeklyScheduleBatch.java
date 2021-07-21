package com.jhallat.todo.scheduler.batch;

import com.jhallat.todo.scheduler.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@ApplicationScoped
public class WeeklyScheduleBatch implements Batch {

    private static final Logger LOG = Logger.getLogger(WeeklyScheduleBatch.class);

    @Inject
    ToDoRepository toDoRepository;

    @Inject
    ScheduledTaskRepository scheduledTaskRepository;

    @Inject
    ScheduleRepository scheduleRepository;

    @Override
    public String getKey() {
        return "WEEKLY_SCHEDULE";
    }

    @Override
    public BatchResponse execute(LocalDate date) {
        try {
            AtomicInteger added = new AtomicInteger(0);
            AtomicInteger updated = new AtomicInteger(0);
            var currentDay = date.getDayOfWeek();
            var taskMap = toDoRepository.findAllForToday()
                    .stream()
                    .filter(item -> item.taskId() > 0)
                    .collect(Collectors.toMap(ToDo::taskId, todo -> todo));
            var weeklySchedules = scheduleRepository.getWeeklySchedules();
            weeklySchedules.forEach(weeklySchedule -> {
                if (applicableForDay(weeklySchedule, currentDay)) {
                    var tasks = scheduledTaskRepository.getScheduledTasks(weeklySchedule.id());
                    tasks.forEach(task -> {
                        if (taskMap.containsKey(task.taskId())) {
                            var todo = taskMap.get(task.taskId());
                            toDoRepository.updateQuantity(todo.id(), task.taskQuantity());
                            updated.getAndIncrement();
                        } else {
                            CreateToDoDTO todo = new CreateToDoDTO(task.taskDescription(),
                                    task.taskId(),
                                    task.taskQuantity(),
                                    task.goalId(),
                                    task.goalDescription());
                            toDoRepository.insertToDo(todo);
                            added.getAndIncrement();
                        }
                    });
                }
            });
            return new BatchResponse(true, updated.get(), added.get());
        } catch (Exception exception) {
            LOG.error("Error occurred in weekly batch", exception);
            return new BatchResponse(false, 0, 0);
        }
    }

    private boolean applicableForDay(WeeklySchedule weeklySchedule, DayOfWeek currentDay) {
        switch (currentDay) {
            case SUNDAY:
                return weeklySchedule.selectedDays().sunday();
            case MONDAY:
                return weeklySchedule.selectedDays().monday();
            case TUESDAY:
                return weeklySchedule.selectedDays().tuesday();
            case WEDNESDAY:
                return weeklySchedule.selectedDays().wednesday();
            case THURSDAY:
                return weeklySchedule.selectedDays().thursday();
            case FRIDAY:
                return weeklySchedule.selectedDays().friday();
            case SATURDAY:
                return weeklySchedule.selectedDays().saturday();
        }
        return false;
    }
}
