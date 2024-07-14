package com.neu.buryingpoint.process.impl;

import com.neu.buryingpoint.model.RecordParams;
import com.neu.buryingpoint.process.ExceptionProcessor;

public class DefaultExceptionProcessor implements ExceptionProcessor {
    @Override
    public RecordParams process(RecordParams params, Throwable ex, Object[] args) {
        return params;
    }

}
