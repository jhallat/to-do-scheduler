package com.jhallat.todo.scheduler;

import com.jhallat.todo.scheduler.batchstatus.BatchService;
import com.jhallat.todo.scheduler.batchstatus.BatchStatus;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/batch-status")
public class BatchStatusController {

    private static final Logger LOG = Logger.getLogger(BatchStatusController.class);

    @Inject
    BatchService batchService;

    @Inject
    ToDoScheduler toDoScheduler;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GET
    public List<BatchStatus> getBatchStatus(@QueryParam("day") String day) {

        LocalDate date = LocalDate.parse(day, formatter);
        return batchService.getStatusForDate(date);
    }

    @POST
    @Path("/uncompleted")
    public void runUncompletedBatches() {
        LOG.info("Run uncompleted batches called from controller");
        toDoScheduler.updateToDoList();
    }

}
