package com.neu.buryingpoint.annontion;

import com.neu.buryingpoint.process.ArgProcessor;
import com.neu.buryingpoint.process.RecordProcessor;
import com.neu.buryingpoint.process.impl.DefaultArgProcessor;
import com.neu.buryingpoint.process.impl.DefaultRecordProcessor;
import org.springframework.data.relational.core.sql.In;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Record {
    /**
     * 标识埋点名称
     */
    String bid();

    /**
     * 是否记录操作
     */
    String isRecord();

    /**
     * 允许用户自定义处理参数
     * @return
     */
    Class<? extends ArgProcessor> argProcessor() default DefaultArgProcessor.class;

    Class<? extends RecordProcessor> recordProcessor() default DefaultRecordProcessor.class;
}
