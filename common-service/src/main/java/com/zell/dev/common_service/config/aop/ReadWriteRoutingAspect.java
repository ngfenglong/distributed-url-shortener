package com.zell.dev.common_service.config.aop;

import com.zell.dev.common_service.config.annotation.ReadOnly;
import com.zell.dev.common_service.config.annotation.WriteOnly;
import com.zell.dev.common_service.config.routing.RoutingContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
@Order(1)
public class ReadWriteRoutingAspect {

    @Around("@annotation(readOnly)")
    public Object routeRead(ProceedingJoinPoint joinPoint, ReadOnly readOnly) throws Throwable {
        try {
            RoutingContext.setReadOnly();
            return joinPoint.proceed();
        } finally {
            RoutingContext.clear();
        }
    }

    @Around("@annotation(writeOnly)")
    public Object routeWrite(ProceedingJoinPoint joinPoint, WriteOnly writeOnly) throws Throwable {
        try {
            RoutingContext.setWriteOnly();
            return joinPoint.proceed();
        } finally {
            RoutingContext.clear();
        }
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object routeTransactional(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        if (transactional.readOnly()) {
            RoutingContext.setReadOnly();
        } else {
            RoutingContext.setWriteOnly();
        }

        try {
            return joinPoint.proceed();
        } finally {
            RoutingContext.clear();
        }
    }

}
