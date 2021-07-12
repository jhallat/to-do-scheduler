package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;

@Path("/api/schedules")
@RegisterRestClient
public interface ScheduleRestClient {

   @GET
   Set<WeeklySchedule> getWeeklySchedules();

}
