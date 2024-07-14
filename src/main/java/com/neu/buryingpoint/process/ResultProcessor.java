package com.neu.buryingpoint.process;

import com.neu.buryingpoint.model.RecordParams;

public interface ResultProcessor{
    RecordParams process(RecordParams params, Object result, Object[] args);
}
