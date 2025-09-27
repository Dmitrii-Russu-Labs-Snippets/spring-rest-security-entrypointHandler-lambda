# Spring REST Security EntryPoint Handler (Lambda)

This project demonstrates how to customize Spring Security error responses using **lambdas directly in `SecurityConfig`**.

## Features
- **401 Unauthorized** → handled with simple custom JSON.  
- **403 Forbidden** → handled with `ProblemDetail` (RFC7807) for standardized API error responses.  
- JSON responses include `status`, `timestamp`, `path`, and other useful fields.  
- Compact implementation with lambdas, no separate components or beans needed.  
- Easy to integrate into small projects or demos.

## Example 401 Response
```json
{
  "status": 401,
  "error": "Authentication failed",
  "timestamp": "2025-09-27T10:00:00.000Z",
  "path": "/auth/user"
}
```

## Example 403 Response
```json
{
  "type": "about:blank",
  "title": "Forbidden",
  "status": 403,
  "detail": "Access Denied",
  "instance": "/auth/admin",
  "timestamp": "2025-09-27T10:01:00.000Z",
  "traceId": "123e4567-e89b-12d3-a456-426614174000"
}
```
## Tech Stack

Java 25

Spring Boot 3+

Spring Security 6+

##How to Run
```
./mvnw spring-boot:run
```

Call protected endpoints to see JSON and ProblemDetail responses for 401/403:

```
curl http://localhost:8080/auth/user
```

```
curl -u ann@gmail.com:1234 http://localhost:8080/auth/admin
```

Related Repositories:

- spring-rest-security-entrypointHandler-component – AuthenticationEntryPoint & AccessDeniedHandler as separate components

- spring-rest-security-entrypointHandler-bean – Same handlers as Spring beans
