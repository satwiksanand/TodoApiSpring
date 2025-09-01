package com.github.satwiksanand.TodoApiSpring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMonitorAspect {

    //this is an advice which tells us when this action should run, learn about advice, join points and point cuts.
    //wherever we use the TimeMonitor annotation is the join point.
    //be cautious while using these advices, for like the around advice fully wraps the method
    //and the method is not executed until we use join point proceed method.
    @Around("@annotation(TimeMonitor)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        System.out.println(joinPoint.getSignature() + " executed in " + (end - start) + "ms");
        return result;
    }

}
