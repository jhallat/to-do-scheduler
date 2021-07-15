package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

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
    @Path("/{id}/active-date")
    void updateTimestamp(@PathParam("id") long id, ToDoActiveDate activeDate);

    @POST
    @Path("/quantity-adjustment")
    void updateQuantity(UpdateQuantityToDoDTO todo);

    @POST
    void insertToDo(CreateToDoDTO todo);
}
