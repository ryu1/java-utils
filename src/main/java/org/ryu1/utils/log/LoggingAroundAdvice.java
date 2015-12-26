package org.ryu1.utils.log;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.ryu1.utils.RecursiveToSrtingStyle;

@Aspect
public class LoggingAroundAdvice{

    /** アプリケーションログ */
    private Log log;

    final long TIMELIMIT = 60000;

    @Around("execution(* xxx.xxx.xxx.xxx.xxx.xxx.xxx.*.*(..))")
    public Object outputOperationLog(ProceedingJoinPoint pjp) throws Throwable{
        return outputLog(pjp);
    }
    
    @Around("execution(* xxx.xxx.xxx.xxx.xxx.xxx.xxx.*.*(..))")
    public Object outputEdyHandlerLog(ProceedingJoinPoint pjp) throws Throwable{
        return outputLog(pjp);
    }

    @Around("execution(* xxx.xxx.xxx.xxx.xxx.xxx.xxx.*.*(..))")
    public Object outputSpModeHandlerLog(ProceedingJoinPoint pjp) throws Throwable{
        return outputLog(pjp);
    }

    public Object outputLog(ProceedingJoinPoint pjp) throws Throwable{
        log = LogFactory.getLog(pjp.getTarget().getClass());
        StopWatch stopWatch = new StopWatch();
        log.info("START:" + format(pjp, pjp.getArgs()));
        stopWatch.start();
        Object rtnObj = pjp.proceed();
        stopWatch.stop();
        if (stopWatch.getTime() < TIMELIMIT) {
            log.info("END[" + stopWatch.getTime() + " ms]:" + format(pjp, rtnObj));
        } else {
            log.error("END[" + stopWatch.getTime() + " ms]:" + format(pjp, rtnObj));
        }
        return rtnObj;
    }

    private String format(ProceedingJoinPoint pjp, Object object) {
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(object,
                new RecursiveToSrtingStyle("xxx.xxx.xxx.xxx"));

        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();

        return  className + "." + methodName + "[" + builder.toString() + "] ";
    }

}
