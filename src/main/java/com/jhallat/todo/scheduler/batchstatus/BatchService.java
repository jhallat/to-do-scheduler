package com.jhallat.todo.scheduler.batchstatus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BatchService {

    @Inject
    EntityManager entityManager;

    public BatchStatus getStatus(String key, LocalDate date) {

        List<BatchStatus> status = (List<BatchStatus>) entityManager.createQuery("""    
             SELECT status 
             FROM BatchStatus status 
             WHERE status.batchKey = :key 
               AND status.batchDate = :date""")
        .setParameter("key", key)
        .setParameter("date", date)
        .getResultList();

        if (status.isEmpty()) {
            BatchStatus notStarted = new BatchStatus();
            notStarted.setId(0L);
            notStarted.setBatchKey(key);
            notStarted.setBatchStatus(Status.NOT_STARTED);
            notStarted.setBatchDate(date);
            return notStarted;
        }
        return status.get(0);
    }

    @Transactional
    public void updateStatus(BatchStatus status) {
        entityManager.merge(status);
        entityManager.flush();
    }
}
