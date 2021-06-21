package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

//TODO This should be implemented as a REST client, and not access the database directly !!!
@ApplicationScoped
public class ToDoRepository {

    @Inject
    @RestClient
    ToDoRestClient toDoRestClient;

     public List<ToDo> findAllIncomplete() {
        return toDoRestClient.getIncomplete().stream().toList();
     }

    public void updateTimestamp(long id, String timestamp) {
        toDoRestClient.updateTimestamp(id, new ToDoTimestamp(timestamp));
    }
}
