package com.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;

/**
 * Created by amarendra on 31/10/16.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Pointcut("@annotation(Loggable)")
    public void annotationPointCutDefinition(){
    }

    @Pointcut("execution(* *(..))")
    public void atExecution(){}

    @Around("@annotation(Loggable) && execution(* *(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnObject = null;
        try {
            log.info("hijacked method : " + joinPoint.getSignature().getName());
            log.info("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
            log.info("Around before is running!");
            returnObject = joinPoint.proceed();//If no exception is thrown we should land here and we can modify the returnObject, if we want to.
            log.info("Around after is running!");
        } catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            //If we want to be sure that some of our code is executed even if we get an exception
            //log.info("YourAspect's aroundAdvice's body is now executed After yourMethodAround is called.");
        }
        return returnObject;
    }

    @After("annotationPointCutDefinition() && atExecution()")
    public void printNewLine(JoinPoint pointcut){
        //System.out.print("\n\r");
    }

}
