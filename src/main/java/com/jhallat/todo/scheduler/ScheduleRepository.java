package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ScheduleRepository {

    @Inject
    @RestClient
    ScheduleRestClient scheduleRestClient;

    public List<ScheduleForDay> getScheduleForDay(String formattedScheduleDate) {
        return scheduleRestClient.getScheduleForDay(formattedScheduleDate).stream().toList();
    }

    public void updateWeeklyMaxReached(long taskId, boolean maxReached) {
        scheduleRestClient.updateWeeklyMaxReached(new WeeklyMaxReachedDTO(taskId, maxReached));
    }
}
