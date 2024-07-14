package com.neu.buryingpoint.annontion;

import com.neu.buryingpoint.process.ExceptionProcessor;
import com.neu.buryingpoint.process.ResultProcessor;

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
    String bid() default "undefined";

    /**
     * 是否记录操作
     */
    String isRecord() default "true";

    /**
     * 正常时设置参数，可由sdk处理
     * @return
     */
    Class<? extends ResultProcessor> resultProcessor() default ResultProcessor.class;

    /**
     * 异常时设置参数，可由sdk处理
     * @return
     */
    Class<? extends ExceptionProcessor> exceptionProcessor() default ExceptionProcessor.class;

}
