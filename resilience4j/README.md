# Circuitbreaker, Retry and Rate Limiter with resilience4j
**Resilience4j's** Circuit Breaker - a powerful library that protects our application from potential failures!

** Retry**  - It provides powerful functionality to handle transient failures and retry operations that may fail initially

** Rate Limiter** - Rate limiting is a technique used to control the flow of requests to a system or API.
## What's It All About?

Resilience4j is a lightweight fault tolerance library designed for functional programming. Resilience4j provides higher-order functions (decorators) to enhance any functional interface, lambda expression or method reference with a Circuit Breaker, Rate Limiter, Retry or Bulkhead. You can stack more than one decorator on any functional interface, lambda expression or method reference. The advantage is that you have the choice to select the decorators you need and nothing else.

**Please note, the fallback method must return the same type as the original method and have same method signature. Otherwise, the FallbackExecutor won't locate a compatible fallback method and will throw a NoSuchMethodException.**


## Let's try the demo
In this example, we are working with two microservices: the Caller Service calls another service , while the Called Service get called from caller service. 

Imagine if our called service encounters an issue. How should our system respond, and how does the Circuit-Breaker pattern step in to stop the entire system from collapsing, allowing it the space to recover.

![circuitBreakerFLow](https://github.com/user-attachments/assets/e7140a7f-0683-4152-9201-d30f62db3a3d)


**Response:**
{"CalledService is called from CallerService"}


## Let's close the called Service and try the same endpoint in browser

```
http://localhost:8080/CallerService

```
**Response:**
{"This is a fallback method for CallerService"}

### Congratulations! The circuit breaker is working. Instead of getting an internal server error, we received a message from the fallback method we set up.

```
@GetMapping
    @CircuitBreaker(name = CALLED_SERVICE, fallbackMethod = "serviceAFallback")
   // @Retry(name = CALLED_SERVICE)
   // @RateLimiter(name = CALLED_SERVICE)
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
```
### Rate Limiting

Can also try by uncommenting the @RateLimiter(name = CALLED_SERVICE)

###Rate limiting is an imperative technique to prepare your API for scale and establish high availability and reliability of your service. But also, this technique comes with a whole bunch of different options of how to handle a detected limits surplus, or what type of requests you want to limit.
You can play around by changing the configuration value in application.yaml of CallerService

### Retry
Can also try by uncommenting the @Retry(name = CALLED_SERVICE)
Automatically retry a failed remote operation
You can play around by changing the configuration value in application.yaml of CallerService



