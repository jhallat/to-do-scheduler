package com.jhallat.todo.scheduler;

import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.SUNDAY;

@ApplicationScoped
public class ToDoScheduler {

    private static final Logger LOG = Logger.getLogger(ToDoScheduler.class);

    @Inject
    ToDoRepository toDoRepository;

    @Inject
    ScheduleRepository scheduleRepository;

    @Inject
    ScheduledTaskRepository scheduledTaskRepository;


    @Scheduled(cron="0 0 0 * * ?")
    @Retry(delay = 3_600_000L, maxDuration = 43_200_200L)
    void updateToDoList() {
        LOG.info("Starting schedule process ...");
        try {
            var formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            var currentDate = LocalDate.now();
            var formattedDate = currentDate.format(formatter);
            var todos = toDoRepository.findAllIncomplete();
            int count = 0;
            for (var todo : todos) {
                if (todo.timestamp().compareTo(formattedDate) < 0) {
                    toDoRepository.updateTimestamp(todo.id(), formattedDate);
                    count++;
                }
            }
            LOG.info(String.format("%s to do items updated.", count));
            var currentDay = currentDate.getDayOfWeek();
            LOG.info("Checking current weekly schedules for " + currentDay);
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
                            LOG.info("Updated " + task.taskDescription());
                        } else {
                            CreateToDoDTO todo = new CreateToDoDTO(task.taskDescription(), task.taskId(), task.taskQuantity());
                            toDoRepository.insertToDo(todo);
                             LOG.info("Added " + task.taskDescription());
                        }
                    });
                }
            });
            LOG.info("Completed weekly schedule");
        } catch (Exception exception) {
            LOG.error("Error occurred during scheduling.", exception);
            throw exception;
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
