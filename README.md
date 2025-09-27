# Spring REST Security EntryPoint Handler (Lambda)

Custom `AuthenticationEntryPoint` and `AccessDeniedHandler` implemented as **lambdas in `SecurityConfig`**.
Returns custom JSON for `401 Unauthorized` and `ProblemDetail` (RFC7807) for `403 Forbidden`.

---

## Overview

This repository demonstrates a minimal and compact way to customize Spring Security error handling using **lambdas directly in `SecurityConfig`**. Instead of creating separate component classes or beans, both `AuthenticationEntryPoint` and `AccessDeniedHandler` are implemented inline as lambda expressions.  

This keeps the setup very concise — ideal for demos, prototypes, or smaller projects — while still returning consistent JSON error responses:  
- **401 Unauthorized** → custom JSON body  
- **403 Forbidden** → `ProblemDetail` (RFC 7807) format  

Implementation difference vs other repos: handlers are defined inline as lambdas in the `SecurityFilterChain`,  not as [@Component classes](https://github.com/Dmitrii-Russu-Labs-Snippets/spring-rest-security-entrypointHandler-component)  or [@Bean methods](https://github.com/Dmitrii-Russu-Labs-Snippets/spring-rest-security-entrypointHandler-bean).

---

## Features
- 401 Unauthorized → simple custom JSON (`status`, `error`, `message`, `timestamp`, `path`)
- 403 Forbidden → RFC7807 `ProblemDetail` (`type`, `title`, `status`, `detail`, `instance`, `timestamp`)
- Compact implementation using lambdas in `SecurityFilterChain` (no separate components)
- Easy to adapt to production (inject `ObjectMapper`, integrate MDC/tracing for `traceId`)

---

## Example 401 response

Content-Type: `application/json`
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Authentication failed",
  "timestamp": "2025-09-27T10:00:00Z",
  "path": "/auth/user"
}
```

## Example 403 response

Content-Type: `application/problem+json`
```json
{
  "type": "about:blank",
  "title": "Forbidden",
  "status": 403,
  "detail": "Access Denied",
  "instance": "/auth/admin",
  "timestamp": "2025-09-27T10:01:00Z"
}
```

---

## How to Run
```
./mvnw spring-boot:run
```
---

## Example curl
401 (no credentials)
```
curl -i http://localhost:8080/auth/user
```
401 (wrong credentials)
```
curl -i -u wrong:wrong http://localhost:8080/auth/user
```
403 (authenticated but not authorized)
```
curl -i -u ann:1234 http://localhost:8080/auth/admin
```
200 (authorized)
```
curl -i -u jack:123 http://localhost:8080/auth/admin
```
---

## Related

- [spring-rest-security-entrypointHandler-component](https://github.com/Dmitrii-Russu-Labs-Snippets/spring-rest-security-entrypointHandler-component) — handlers as separate components  
- [spring-rest-security-entrypointHandler-bean](https://github.com/Dmitrii-Russu-Labs-Snippets/spring-rest-security-entrypointHandler-bean) — handlers as Spring beans

