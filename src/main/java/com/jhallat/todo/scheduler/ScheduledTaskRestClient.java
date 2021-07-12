package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Set;

@Path("/api/scheduled-task")
@RegisterRestClient
public interface ScheduledTaskRestClient {

    @GET
    @Path("/{scheduleId}")
    Set<ScheduledTask> getScheduledTasks(@PathParam("scheduleId") int scheduleId);

}
