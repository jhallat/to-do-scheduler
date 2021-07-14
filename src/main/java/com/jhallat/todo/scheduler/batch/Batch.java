package com.jhallat.todo.scheduler.batch;

import java.time.LocalDate;

public interface Batch {

    String getKey();
    BatchResponse execute(LocalDate date);

}
