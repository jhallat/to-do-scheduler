package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Set;

@Path("/api/todo")
@RegisterRestClient
public interface ToDoRestClient {

    @GET
    @Path("/incomplete")
    Set<ToDo> getIncomplete();

    @PUT
    @Path("/{id}/timestamp")
    void updateTimestamp(@PathParam("id") long id, ToDoTimestamp timestamp);
}
