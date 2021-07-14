package com.jhallat.todo.scheduler;

import com.jhallat.todo.scheduler.batch.Batch;
import com.jhallat.todo.scheduler.batch.BatchResponse;
import com.jhallat.todo.scheduler.batch.CopyIncompleteBatch;
import com.jhallat.todo.scheduler.batch.WeeklyScheduleBatch;
import com.jhallat.todo.scheduler.batchstatus.BatchService;
import com.jhallat.todo.scheduler.batchstatus.BatchStatus;
import com.jhallat.todo.scheduler.batchstatus.Status;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.SUNDAY;

@ApplicationScoped
@ActivateRequestContext
public class ToDoScheduler {

    private static final Logger LOG = Logger.getLogger(ToDoScheduler.class);

    @Inject
    BatchService batchService;

    @Inject
    CopyIncompleteBatch copyIncompleteBatch;

    @Inject
    WeeklyScheduleBatch weeklyScheduleBatch;


    @Scheduled(cron="0 0 0 * * ?")
    @Retry(delay = 3_600_000L, maxDuration = 43_200_200L)
    void updateToDoList() {
        LOG.info("Initializing Batch");
        List<Batch> batches = new ArrayList<>();
        batches.add(copyIncompleteBatch);
        batches.add(weeklyScheduleBatch);
        LocalDate currentDate = LocalDate.now();
        boolean skip = false;
        for (Batch batch: batches) {
            BatchStatus status = null;
            try {
                status = batchService.getStatus(batch.getKey(), currentDate);
            } catch (Exception exception) {
                LOG.error("Exception occurred getting batch status for " + batch.getKey(), exception);
                skip = true;
            }
            if (skip) {
                LOG.warn(String.format("Batch %s skipped", batch.getKey()));
                status.setBatchStatus(Status.NOT_STARTED);
                batchService.updateStatus(status);
                continue;
            }
            if (status.getBatchStatus() != Status.COMPLETED) {
                status.setBatchStatus(Status.IN_PROGRESS);
                try {
                    batchService.updateStatus(status);
                } catch (Exception exception) {
                    LOG.error("Unable to update status for " + batch.getKey(), exception);
                    skip = true;
                    continue;
                }
                LOG.info(String.format("Batch %s started for %s", batch.getKey(), currentDate));
                BatchResponse response = batch.execute(currentDate);
                if (response.success()) {
                    LOG.info(String.format("Batch %s completed. %s added, %s updated", batch.getKey(),
                            response.added(),
                            response.updated()));
                    status.setBatchStatus(Status.COMPLETED);
                    try {
                        batchService.updateStatus(status);
                    } catch (Exception exception) {
                        LOG.error("Unable to update status for " + batch.getKey(), exception);
                        skip = true;
                        continue;
                    }
                } else {
                    LOG.error(String.format("Batch %s failed.", batch.getKey()));
                    skip = true;
                }
            }
        }

    }



}
