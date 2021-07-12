package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.graalvm.nativeimage.Isolates;

import javax.ws.rs.*;
import java.util.Set;

@Path("/api/todo")
@RegisterRestClient
public interface ToDoRestClient {

    @GET
    @Path("/incomplete")
    Set<ToDo> getIncomplete();

    @GET
    @Path("/today")
    Set<ToDo> getTodaysItems();

    @PUT
    @Path("/{id}/timestamp")
    void updateTimestamp(@PathParam("id") long id, ToDoTimestamp timestamp);

    @POST
    @Path("/quantity-adjustment")
    void updateQuantity(UpdateQuantityToDoDTO todo);

    @POST
    void insertToDo(CreateToDoDTO todo);
}
