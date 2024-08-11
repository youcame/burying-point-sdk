package com.neu.buryingpoint.core;

import com.neu.buryingpoint.model.RecordParams;
import com.neu.buryingpoint.annontion.Record;
import com.neu.buryingpoint.model.SystemConfig;
import com.neu.buryingpoint.process.ArgProcessor;
import com.neu.buryingpoint.process.ExceptionProcessor;
import com.neu.buryingpoint.process.RecordProcessor;
import com.neu.buryingpoint.process.ResultProcessor;
import com.neu.buryingpoint.util.ArgsUtil;
import lombok.Data;
import org.apache.dubbo.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;


@Data
@Aspect
@Component
public class RecordAspect implements Ordered {

    private RecordProcessor recordProcessor;
    private ArgProcessor argProcessor;
    @Resource
    private SystemConfig systemConfig;

    @Autowired
    public RecordAspect(RecordProcessor recordProcessor, ArgProcessor argProcessor) {
        this.recordProcessor = recordProcessor;
        this.argProcessor = argProcessor;
    }

    @Pointcut("@annotation(com.neu.buryingpoint.annontion.Record)")
    public void record(){}

    @Around("record()")
    public Object RecordOperation(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Record record = method.getAnnotation(Record.class);
        String isOpen = record.isRecord();
        //开关未打开，则不做处理，直接返回
        if(!"true".equals(isOpen)) {
            return joinPoint.proceed();
        }
        try {
            Object result = joinPoint.proceed();
            sendBuryingPoint(joinPoint, result, record);
            return result;
        } finally {
            //closeContext()
        }
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    private void getParamFromAnnontion(RecordParams params, Record record) {
        params.setBid(StringUtils.isBlank(record.bid()) ? "" : record.bid());
        params.setDescription(record.bid());
    }
    private void initContext(){
//        if(RpcContext.getServiceContext().getAttachment()) {
//
//        }
    }

    private void sendBuryingPoint(ProceedingJoinPoint joinPoint, Object result, Record record){
        initContext();
        ResultProcessor resultProcessor;
        ExceptionProcessor exceptionProcessor;
        RecordParams params = new RecordParams();
        try {
            resultProcessor = record.resultProcessor().newInstance();
            exceptionProcessor = record.exceptionProcessor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //由于每个系统的返回值都不相同，此处自定义处理返回值中的信息（例如code码值）
        if (result instanceof Throwable) {
            Throwable ex = (Throwable) result;
            exceptionProcessor.process(params, ex, joinPoint.getArgs());
        } else {
            resultProcessor.process(params, result, joinPoint.getArgs());
        }
        //从方法入参中获取参数值并记录
        ArgsUtil.getParamFromMethodArgs(params, joinPoint);
        //从Starter中获取参数
        getArgsFromStarter(params);
        //从注解中获取参数值并记录
        getParamFromAnnontion(params, record);
        //系统自定义设置参数
        argProcessor.process(params);
        //用户自定义发送埋点
        recordProcessor.process();
    }

    private void getArgsFromStarter(RecordParams params) {
        params.setSysId(systemConfig.getSysId());
        params.setSysDescription(systemConfig.getSysDescription());
    }
}
