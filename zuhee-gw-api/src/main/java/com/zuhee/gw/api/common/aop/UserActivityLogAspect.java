package com.zuhee.gw.api.common.aop;

import com.zuhee.gw.api.domain.log.AccessLog;
import com.zuhee.gw.api.domain.log.AccessLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UserActivityLogAspect {

    private final AccessLogRepository accessLogRepository;

    @Around("@annotation(com.zuhee.gw.api.common.aop.UserActivityLog)")
    public Object logUserActivity(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - start;
            saveLog(joinPoint, executionTime);
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long executionTime) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String userId = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "ANONYMOUS";

        String apiPath = request.getRequestURI();
        String method = request.getMethod();
        String clientIp = request.getRemoteAddr();

        if (executionTime > 1000) {
            log.warn("Slow API detected: {} {} took {}ms", method, apiPath, executionTime);
        }

        AccessLog accessLog = AccessLog.builder()
                .userId(userId)
                .apiPath(apiPath)
                .method(method)
                .executionTime(executionTime)
                .clientIp(clientIp)
                .build();

        accessLogRepository.save(accessLog);
    }
}
