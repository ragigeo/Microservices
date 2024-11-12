package com.george.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/CallerService")
public class CallerServiceController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL
            = "http://localhost:8081/";

    private static final String CALLED_SERVICE = "calledService";

    int count=1;

    @GetMapping
   // @CircuitBreaker(name = CALLED_SERVICE, fallbackMethod = "serviceAFallback")
   // @Retry(name = CALLED_SERVICE)
    @RateLimiter(name = CALLED_SERVICE)
    public String serviceA() {

        String url = BASE_URL + "CalledService";
        System.out.println("Retry method called " + count++ + " times at " + new Date());
        return restTemplate.getForObject(
                url,
                String.class
        );
    }

    public String serviceAFallback(Exception e) {
        return "This is a fallback method for CallerService";
    }
}
