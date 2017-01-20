package com.datapine.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLogger {

    private static final Logger LOG = LoggerFactory.getLogger(MethodLogger.class);

    @Before("execution(* *(..)) && @annotation(LoggableAOP)")
    public void before(JoinPoint point) throws Throwable {
        LOG.info(String.format("Method: %s called with parameters: (%s)",
                                point.getSignature().getName(),
                                Arrays.toString(point.getArgs())));
    }

}