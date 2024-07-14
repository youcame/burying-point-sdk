package com.neu.buryingpoint.process.impl;

import com.neu.buryingpoint.model.RecordParams;
import com.neu.buryingpoint.process.ResultProcessor;

public class DefaultResultProcessor implements ResultProcessor {


    @Override
    public RecordParams process(RecordParams params, Object result, Object[] args) {
        return params;
    }
}
