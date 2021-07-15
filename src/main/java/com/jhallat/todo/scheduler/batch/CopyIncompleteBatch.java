package com.jhallat.todo.scheduler.batch;

import com.jhallat.todo.scheduler.ToDoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class CopyIncompleteBatch implements Batch {

    @Inject
    ToDoRepository toDoRepository;

    @Override
    public String getKey() {
        return "COPY_INCOMPLETE";
    }

    @Override
    public BatchResponse execute(LocalDate date) {

        try {
            var formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            var formattedDate = date.format(formatter);
            var todos = toDoRepository.findAllIncomplete();
            int added = 0;
            for (var todo : todos) {
                if (todo.activeDate().compareTo(formattedDate) < 0) {
                    toDoRepository.updateActiveDate(todo.id(), formattedDate);
                    added++;
                }
            }

            return new BatchResponse(true, 0, added);
        } catch (Exception exception) {
            return new BatchResponse(false, 0, 0);
        }
    }
}
