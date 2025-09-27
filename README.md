# Spring REST Security EntryPoint Handler (Lambda)

Custom `AuthenticationEntryPoint` and `AccessDeniedHandler` implemented as **lambdas in `SecurityConfig`**.
Returns custom JSON for `401 Unauthorized` and `ProblemDetail` (RFC7807) for `403 Forbidden`.

## Features
- 401 Unauthorized → simple custom JSON (`status`, `error`, `message`, `timestamp`, `path`)
- 403 Forbidden → RFC7807 `ProblemDetail` (`type`, `title`, `status`, `detail`, `instance`, `timestamp`)
- Compact implementation using lambdas in `SecurityFilterChain` (no separate components)
- Easy to adapt to production (inject `ObjectMapper`, integrate MDC/tracing for `traceId`)

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

## How to Run
```
./mvnw spring-boot:run
```
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

Related

- spring-rest-security-entrypointHandler-component — handlers as separate components

- spring-rest-security-entrypointHandler-bean — handlers as Spring beans
