package org.ryu1.utils.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingThrowsAdvice {

    /** アプリケーションログ */
    private Log log;

    @AfterThrowing(pointcut = "execution(* xxx.xxx.xxx.xxx.xxx..*.*(..))", throwing = "e")
    public void outputLog(JoinPoint pjp, Throwable e) {
            log = LogFactory.getLog(pjp.getTarget().getClass());
            String className = pjp.getTarget().getClass().getSimpleName();
            String methodName = pjp.getSignature().getName();
            if (e instanceof RuntimeException || e instanceof Error) {
                log.error(className + "." + methodName, e);
            } else {
                log.debug(className + "." + methodName, e);
            }
        }
}
