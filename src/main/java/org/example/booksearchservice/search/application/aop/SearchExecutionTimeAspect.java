package org.example.booksearchservice.search.application.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.booksearchservice.search.application.dto.SearchMetaData;
import org.example.booksearchservice.search.application.dto.SearchResponse;
import org.springframework.stereotype.Component;

import static java.lang.System.currentTimeMillis;

@Aspect
@Component
@RequiredArgsConstructor
public class SearchExecutionTimeAspect {

    @Around("execution(* org.example.booksearchservice.search.application.service.SearchService.*(..))")
    public Object recordExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = currentTimeMillis() - start;

        if (result instanceof SearchResponse response) {
            return SearchResponse.of(
                response.searchQuery(),
                response.pageInfo(),
                response.books(),
                SearchMetaData.of(
                    executionTime,
                    response.searchMetaData().strategy()
                )
            );
        }

        return result;
    }
}
