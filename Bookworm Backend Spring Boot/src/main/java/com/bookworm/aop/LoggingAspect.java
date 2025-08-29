package com.bookworm.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.bookworm.service.*Service.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("Calling: " + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.bookworm.service.*Service.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("Completed: " + joinPoint.getSignature() + " with result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.bookworm.service.*Service.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.err.println("Exception in: " + joinPoint.getSignature() + " => " + ex.getMessage());
    }
    
@Around("execution(* com.bookworm.service.*Service.*(..))")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;

        try {
            result = joinPoint.proceed(); 
            long duration = System.currentTimeMillis() - start;
            System.out.println("Completed: " + joinPoint.getSignature()
                    + " in " + duration + " ms with result: " + result);
        } catch (Throwable e) {
            System.err.println("Exception in: " + joinPoint.getSignature() + " -> " + e.getMessage());
            throw e; 
        }

        return result;
    }

   
}