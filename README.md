# Children Budget

Children Budget is a Spring Boot application that helps parents keep track of their children's balances and transactions.

## Features

- Parents can manage balances and transactions for each child via authenticated `/users/{id}` pages.
- Children can view their balances and transaction histories through public read-only `/dashboard/{id}` pages.
- Basic authentication protects all management endpoints, while dashboards stay public.
- A Quartz-powered daily job (run on a cron schedule) credits each child with an age-based bonus, keeping allowances fresh automatically.

## Technology Stack

- Java 25
- Spring Boot 3
- Spring Data JDBC
- Spring Security
- Quartz Scheduler
- Freemarker Templates

## Getting Started

1. Set up environment variables:
   - `APP_BASIC_AUTH_USERNAME`
   - `APP_BASIC_AUTH_PASSWORD`
2. Run database migrations and start the application:
   ```bash
   ./mvnw clean spring-boot:run
   ```
3. Access endpoints via Basic Auth or use the `/dashboard/{userId}` route for read-only views.
