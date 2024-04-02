package quizbank.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
  private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

  @Around("execution(* quizbank.controller.*.*(..))")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = attr.getRequest();

    String httpMethod = request.getMethod();
    String uri = request.getRequestURI();

    logger.info("API request: " + httpMethod + " " + uri + " with arguments: " + Arrays.toString(joinPoint.getArgs()));
    long start = System.currentTimeMillis();

    Object result = joinPoint.proceed();

    long duration = System.currentTimeMillis() - start;

    if (result instanceof ResponseEntity) {
      ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
      logger.info("API response for " + httpMethod + " " + uri + ": status=" + responseEntity.getStatusCode() + ", duration=" + duration + "ms");
    } else {
      // For non-ResponseEntity results
      logger.info("API response for " + httpMethod + " " + uri + ": result=" + result + ", duration=" + duration + "ms");
    }
    return result;
  }
}
