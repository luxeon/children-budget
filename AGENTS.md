# Repository Guidelines

## Project Structure & Module Organization
Source lives under `src/main/java/fyi/dslab/family_budget`, with `FamilyBudgetApplication` bootstrapping the Spring Boot context and feature packages expected to sit beside it (e.g., `budget`, `account`, `report`). Shared templates or static assets belong in `src/main/resources`; configuration defaults reside in `application.properties`. Keep tests in `src/test/java`, mirroring the main-package layout so service, repository, and controller tests stay co-located with their production counterparts.

## Build, Test, and Development Commands
- `./mvnw clean verify` — compile, run unit tests, and fail fast on style or dependency issues; use before every push.
- `./mvnw spring-boot:run` — start the local server with the active profile and hot reload classpath resources, ideal for manual testing.
- `./mvnw clean package -DskipTests` — produce an executable jar in `target/` when you need a quick artifact (only skip tests with a clear justification in the PR).

## Coding Style & Naming Conventions
Use Java 25 features allowed by Spring Boot 3.5, stick to 4-space indentation, and keep lines under ~120 characters. Name Spring components with intent-revealing suffixes (`BudgetController`, `AccountRepository`) and keep package names lowercase with underscores only where the generator already uses them. Favor Lombok for boilerplate but document any non-obvious annotations inline; Freemarker templates should follow kebab-case filenames matching their controller endpoints.

## Testing Guidelines
Write JUnit 5 tests via `spring-boot-starter-test`; default class names to `SomethingTests`. Cover every controller path with MVC tests or slice tests, and target at least 80% branch coverage for critical budgeting logic. When touching JDBC code, supply lightweight data fixtures under `src/test/resources` or leverage embedded databases so tests stay deterministic.

## Commit & Pull Request Guidelines
This export omits `.git`, but the upstream DSLab workflow mirrors Conventional Commits: `<type>: <imperative summary>` (e.g., `feat: add allowance projections`). Reference related issues in the body, describe schema or API changes plainly, and attach UI screenshots if the Freemarker views change. PRs should list test evidence (`./mvnw clean verify`) plus any manual checks so reviewers can reproduce results quickly.

## Security & Configuration Tips
Do not store credentials in `application.properties`; load secrets from environment variables or a profile-specific override ignored by Git. Keep JDBC URL, user, and password configurable, and redact any production-only settings from sample configs. Review dependency bumps for CVEs and mention mitigation steps in the PR when libraries touch authentication or financial calculations.
