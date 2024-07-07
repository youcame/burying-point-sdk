package com.neu.buryingpoint.core;

import com.neu.buryingpoint.model.RecordParams;
import com.neu.buryingpoint.annontion.Record;
import com.neu.buryingpoint.process.ArgProcessor;
import com.neu.buryingpoint.process.RecordProcessor;
import com.neu.buryingpoint.util.ArgsUtil;
import lombok.Data;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
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
        String isOpen = record.isRecord();
        if(!"true".equals(isOpen)) {
            return joinPoint.proceed();
        }
        initContext();
        ArgProcessor argProcessor = record.argProcessor().newInstance();
        RecordProcessor recordProcessor = record.recordProcessor().newInstance();
        RecordParams params = new RecordParams();
        //从方法入参中获取参数值并记录
        ArgsUtil.getParamFromMethodArgs(params, joinPoint);
        //从注解中获取参数值并记录
        getParamFromAnnontion(params, record);
        //系统自定义设置参数
        argProcessor.process(params);
        //用户自定义发送埋点
        recordProcessor.process();
        return joinPoint.proceed();
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
}
