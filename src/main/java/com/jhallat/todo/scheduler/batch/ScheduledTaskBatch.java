package com.jhallat.todo.scheduler.batch;

import com.jhallat.todo.scheduler.CreateToDoDTO;
import com.jhallat.todo.scheduler.ScheduleRepository;
import com.jhallat.todo.scheduler.ToDo;
import com.jhallat.todo.scheduler.ToDoRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@ApplicationScoped
public class ScheduledTaskBatch implements Batch {

    private static final Logger LOG = Logger.getLogger(ScheduledTaskBatch.class);

    @Inject
    ToDoRepository toDoRepository;

    @Inject
    ScheduleRepository scheduleRepository;

    @Override
    public String getKey() {
        return "SCHEDULED_TASK";
    }

    @Override
    public BatchResponse execute(LocalDate date) {
        try {
            int added = 0;
            int updated = 0;
            var scheduleFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            var formattedScheduleDate = date.format(scheduleFormatter);
            var checklistFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            var formattedChecklistDate = date.format(checklistFormatter);
            var taskMap = toDoRepository.findAllForActiveDate(formattedChecklistDate)
                    .stream()
                    .filter(item -> item.taskId() > 0)
                    .collect(Collectors.toMap(ToDo::taskId, todo -> todo));
            LOG.info("Returned " + taskMap.size() + " active tasks");
            var schedules = scheduleRepository.getScheduleForDay(formattedScheduleDate);
            for (var schedule : schedules) {
                if (taskMap.containsKey(schedule.taskId())) {
                    if (schedule.quantifiable()) {
                        var todo = taskMap.get(schedule.taskId());
                        toDoRepository.updateQuantity(todo.id(), schedule.quantity());
                        updated++;
                    }
                } else {
                    CreateToDoDTO todo = new CreateToDoDTO(schedule.description(),
                            schedule.taskId(),
                            schedule.quantity(),
                            schedule.goalId(),
                            schedule.goalDescription());
                    //toDoRepository.insertToDo(todo);
                    added++;
                }
            }
            return new BatchResponse(true, updated, added);
        } catch (Exception exception) {
            LOG.error("Error occurred in scheduled task batch", exception);
            return new BatchResponse(false, 0, 0);
        }
    }
}
