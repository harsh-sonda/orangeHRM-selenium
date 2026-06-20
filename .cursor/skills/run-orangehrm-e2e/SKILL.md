---
name: run-orangehrm-e2e
description: Run OrangeHRM E2E tests via Java Maven POM suite or user-selenium MCP. Use when asked to run E2E tests, execute OrangeHRM scenarios, or validate module tests.
---

# Run OrangeHRM E2E Tests

Primary execution is the **Java Page Object Model suite** (72 tests, 14 module classes). Use Maven unless the user explicitly asks for MCP/browser-agent execution.

## Java POM suite (default)

```bash
# Full suite (~2–3 hours)
mvn clean test

# Single module
mvn test -Dtest=AuthTest
mvn test -Dtest=AdminTest

# Visible browser
mvn clean test -Dheadless=false
```

Suite entry: [`testng.xml`](testng.xml) — all classes under `com.orangehrm.tests`.

| Class | Tests |
|-------|-------|
| AuthTest | 6 |
| CommonTest | 3 |
| DashboardTest | 5 |
| AdminTest | 12 |
| PimTest | 6 |
| LeaveTest | 8 |
| TimeTest | 8 |
| RecruitmentTest | 6 |
| PerformanceTest | 5 |
| MyInfoTest | 4 |
| BuzzTest | 3 |
| DirectoryTest | 2 |
| ClaimTest | 1 |
| MaintenanceTest | 3 |

Locators and flows live in [`src/main/java/com/orangehrm/pages/`](src/main/java/com/orangehrm/pages/). Archived step spec: [`docs/e2e-spec.csv`](docs/e2e-spec.csv).

## MCP agent execution (optional)

When the user wants MCP-driven runs, read test methods in `src/test/java/com/orangehrm/tests/` and execute equivalent steps with `user-selenium` MCP:

1. Read MCP tool schemas from `user-selenium` before first call
2. Follow the same flow as the Java `@Test` method (login → navigate → interact → assert)
3. Use page-object locators from Java source (e.g. `LoginPage`, `CommonPage`)
4. On failure, screenshot to `tests/results/{test_id}-fail.png`
5. Write `tests/results/run-report.json` with pass/fail summary

### MCP action hints

| Flow step | MCP tool |
|-----------|----------|
| Open URL | `navigate` |
| Type in field | `send_keys` (once per field) |
| Click | `interact` action `click` |
| Assert text | `get_element_text` |
| Assert URL | `execute_script` → `window.location.href` |
| Wait | `execute_script` with `setTimeout` promise |

### Agent rules

1. **Login:** single `send_keys` per field — double entry causes "Invalid credentials"
2. **Topbar:** click parent menu item before submenu link; wait 500–1500ms
3. **Leave dates:** OrangeHRM `yyyy-dd-mm` (e.g. `2026-01-07` = 1 July 2026)
4. **Error alerts:** wait 2000ms after failed login before asserting
5. Always `close_session` when done

## Demo credentials

- URL: `https://opensource-demo.orangehrmlive.com/web/index.php/auth/login`
- Username: `Admin` / Password: `admin123`
