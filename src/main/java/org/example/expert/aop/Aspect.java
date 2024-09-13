package org.example.expert.aop;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@org.aspectj.lang.annotation.Aspect
public class Aspect {

    @Pointcut("@annotation(org.example.expert.annotation.TrackTime)")
    public void trackTimeAnnotation () {}

    @Around("trackTimeAnnotation()")
    public Object adviceAnnotation(ProceedingJoinPoint joinPoint, AuthUser authUser) throws Throwable {

        LocalDateTime time = LocalDateTime.now();
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        try{
            Object result = joinPoint.proceed();
            return result;
        } finally {
            log.info("요청 사용자 ID: {}", authUser.getId());
            log.info("API 요청 시각: {}", time);
            log.info("요청 URL: {}", request.getRequestURL());
        }
    }
}
