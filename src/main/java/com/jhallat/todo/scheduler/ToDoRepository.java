package com.jhallat.todo.scheduler;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;


@ApplicationScoped
public class ToDoRepository {

    @Inject
    @RestClient
    ToDoRestClient toDoRestClient;

    public List<ToDo> findAllIncomplete() {
        return toDoRestClient.getIncomplete().stream().toList();
     }


    public void updateActiveDate(long id, String activeDate) {
        toDoRestClient.updateTimestamp(id, new ToDoActiveDate(activeDate));
    }

    public void updateQuantity(long id, int quantity) {
         toDoRestClient.updateQuantity(new UpdateQuantityToDoDTO(id, -quantity));
    }

    public void insertToDo(CreateToDoDTO todo) {
         toDoRestClient.insertToDo(todo);
    }

    public List<ToDo> findAllForToday() {
         return toDoRestClient.getTodaysItems().stream().toList();
    }

    public List<ToDo> findAllForActiveDate(String date) { return toDoRestClient.getByActiveDate(date).stream().toList();}
}
