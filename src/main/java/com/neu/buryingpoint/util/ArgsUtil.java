package com.neu.buryingpoint.util;

import com.neu.buryingpoint.model.RecordParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
public class ArgsUtil {

    private static final Logger logger = LoggerFactory.getLogger(ArgsUtil.class);

    private static final String getPattern = "get%s";


    public static void getParamFromMethodArgs(RecordParams recordParams, ProceedingJoinPoint joinPoint) {
        recordParams.setOperateTime(new Date());
        Object[] args = joinPoint.getArgs();
        if(args.length == 0) {
            return;
        }
        //从第一个参数取，一般是request对象
        Object request = args[0];
        String pin = getParam(request, "Pin");
        String source = getParam(request, "Source");
        recordParams.setPin(pin);
        recordParams.setSource(source);
    }

    private static String getParam(Object request, String paramName) {
        try{
            Method method = ReflectionUtils.findMethod(request.getClass(), String.format(getPattern, paramName));
            Object value = ReflectionUtils.invokeMethod(method, request);
            if(value instanceof String) {
                return (String) value;
            }
        } catch (Exception e) {
            logger.error("[链路追踪系统]从入参获取:{},失败,request:{}", paramName, JsonUtils.toJson(request), e);
        }
        return "Not Standard Input Args";
    }
}
