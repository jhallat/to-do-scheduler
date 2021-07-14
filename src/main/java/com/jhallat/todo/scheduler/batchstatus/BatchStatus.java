package com.jhallat.todo.scheduler.batchstatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="batch_status")
public class BatchStatus {

    @Id
    @SequenceGenerator(name="batch_status_id_seq",
                       sequenceName = "batch_status_id_seq",
                       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "batch_status_id_seq")
    private Long id;
    @Column(name="batch_date")
    private LocalDate batchDate;
    @Column(name="batch_key")
    private String batchKey;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status batchStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(LocalDate batchDate) {
        this.batchDate = batchDate;
    }

    public String getBatchKey() {
        return batchKey;
    }

    public void setBatchKey(String batchKey) {
        this.batchKey = batchKey;
    }

    public Status getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(Status batchStatus) {
        this.batchStatus = batchStatus;
    }

}
