package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Set;

@Path("/api/v2/scheduled-task")
@RegisterRestClient
public interface ScheduleV2RestClient {

    @GET
    Set<ScheduleForDay> getScheduleForDay(@QueryParam("day") String day);

}
