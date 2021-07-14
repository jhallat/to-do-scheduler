package com.jhallat.todo.scheduler;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import javax.inject.Inject;

@QuarkusMain
public class SchedulerApplication {
    public static void main(String...args) {
        Quarkus.run(ScheduleApp.class, args);
    }

    public static class ScheduleApp implements QuarkusApplication {
        @Inject
        ToDoScheduler scheduler;

        @Override
        public int run(String...args) throws Exception {
            //Run once on startup. Any subsequent call will be scheduled
            scheduler.updateToDoList();
            Quarkus.waitForExit();
            return 0;
        }
    }
}
