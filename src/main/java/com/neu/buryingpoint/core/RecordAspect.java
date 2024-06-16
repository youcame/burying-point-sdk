package com.neu.buryingpoint.core;

import com.neu.buryingpoint.RecordParams;
import com.neu.buryingpoint.annontion.Record;
import com.neu.buryingpoint.process.ArgProcessor;
import com.neu.buryingpoint.util.ArgsUtil;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;


@Data
@Aspect
@Component
public class RecordAspect implements Ordered {

    @Resource

    @Pointcut("@annotation(com.neu.buryingpoint.annontion.Record)")
    public void record(){}

    @Around("record()")
    public Object RecordOperation(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Record record = method.getAnnotation(Record.class);
        String bid = record.bid();
        String isOpen = record.isRecord();
        ArgProcessor argProcessor = record.argProcessor();

        if(!"true".equals(isOpen)) {
            return joinPoint.proceed();
        }
        RecordParams recordParams = new RecordParams();
        ArgsUtil.preRecordParams(recordParams, joinPoint);

    }

    public int getOrder() {
        return 0;
    }
}
