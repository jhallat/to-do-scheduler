package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ScheduledTaskRepository {

    @Inject
    @RestClient
    ScheduledTaskRestClient scheduledTaskRestClient;

    public List<ScheduledTask> getScheduledTasks(int scheduledId) {
        return scheduledTaskRestClient.getScheduledTasks(scheduledId).stream().toList();
    }

}
