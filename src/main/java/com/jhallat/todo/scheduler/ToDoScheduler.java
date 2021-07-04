package com.jhallat.todo.scheduler;

import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class ToDoScheduler {

    private static final Logger LOG = Logger.getLogger(ToDoScheduler.class);

    @Inject
    ToDoRepository toDoRepository;

    @Scheduled(cron="0 0 0 * * ?")
    @Retry(delay = 3_600_000L, maxDuration = 43_200_200L)
    void updateToDoList() {
        var formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        var currentDate = LocalDate.now().format(formatter);
        var todos = toDoRepository.findAllIncomplete();
        int count = 0;
        for (var todo : todos) {
            toDoRepository.updateTimestamp(todo.id(), currentDate);
            count++;
        }
        LOG.info(String.format("%s to do items updated.", count));
    }

}
