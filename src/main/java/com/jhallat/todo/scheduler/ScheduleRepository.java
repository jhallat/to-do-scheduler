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

    @Inject
    @RestClient
    ScheduleV2RestClient scheduleV2RestClient;

    public List<WeeklySchedule> getWeeklySchedules() {
        return scheduleRestClient.getWeeklySchedules().stream().toList();
    }

    public List<ScheduleForDay> getScheduleForDay(String day) {
        return scheduleV2RestClient.getScheduleForDay(day).stream().toList();
    }
}
