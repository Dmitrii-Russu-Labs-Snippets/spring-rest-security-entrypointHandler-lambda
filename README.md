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
