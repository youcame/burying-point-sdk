package com.neu.buryingpoint.process;

import com.neu.buryingpoint.model.RecordParams;

public interface ExceptionProcessor {
    RecordParams process(RecordParams params, Throwable ex, Object[] args);
}
