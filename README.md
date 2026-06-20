# orangeHRM-selenium

E2E tests for the [OrangeHRM demo site](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login) using two execution models:

1. **Java POM** — Selenium WebDriver + TestNG + Maven (standalone / CI)
2. **MCP agent** — CSV step definitions executed via Cursor + user-selenium MCP

## Demo credentials

| Field    | Value     |
|----------|-----------|
| URL      | https://opensource-demo.orangehrmlive.com/web/index.php/auth/login |
| Username | Admin     |
| Password | admin123  |

## Java POM tests (recommended for local/CI)

### Prerequisites

- JDK 17+
- Maven 3.8+
- Google Chrome

### Run all login tests

```bash
mvn clean test
```

### Run a single test class

```bash
mvn test -Dtest=LoginTest
```

### GitHub Actions CI

Tests run automatically on push and pull requests to `main` via [`.github/workflows/ci.yml`](.github/workflows/ci.yml).

The workflow uses Ubuntu, JDK 17, Chrome (headless), and runs:

```bash
mvn clean test -B -Dheadless=true
```

To trigger manually: **Actions** → **OrangeHRM E2E Tests** → **Run workflow**.

### Configuration

Edit `src/test/resources/config.properties`:

| Property | Description |
|----------|-------------|
| `base.url` | OrangeHRM login URL |
| `implicit.wait.seconds` | WebDriver wait timeout |
| `browser` | Browser name (chrome) |
| `headless` | Run headless when `true` (overridable with `-Dheadless=true`) |

### Project structure

```
src/main/java/com/orangehrm/
├── base/BasePage.java           # Shared wait helpers
├── config/TestConfig.java       # Loads config.properties
└── pages/
    ├── LoginPage.java           # Login page object
    └── DashboardPage.java       # Dashboard page object

src/test/java/com/orangehrm/
├── base/BaseTest.java           # WebDriver lifecycle
├── data/
│   ├── CsvDataProvider.java     # TestNG data provider
│   └── LoginTestData.java       # CSV row POJO
└── tests/LoginTest.java         # Parameterized login tests

src/test/resources/
├── config.properties
└── login-test-data.csv          # Test inputs and expectations
```

### Test data (Java)

File: `src/test/resources/login-test-data.csv`

One row per scenario. Columns: `test_id`, `test_name`, `username`, `password`, `scenario`, `expected_url_contains`, `expected_text`, `expected_text_target`.

The `scenario` column drives test logic in `LoginTest.java` (`valid_login`, `invalid_login`, `empty_username`, etc.).

## MCP agent tests (Cursor)

### Prerequisites

- [Cursor](https://cursor.com) with the **user-selenium** MCP server enabled
- Google Chrome installed locally

### How to run

In Cursor chat, ask:

> Run OrangeHRM login E2E tests from CSV

The agent loads the `run-orangehrm-e2e` skill, reads `tests/csv/login.csv`, and executes each step through Selenium MCP.

To run a single test:

> Run test TC_LOGIN_001 from tests/csv/login.csv

### MCP project structure

```
tests/
├── csv/login.csv          # Step-by-step test cases
├── locators/login.json    # Element locators for MCP
└── results/               # Failure screenshots (gitignored)
.cursor/skills/run-orangehrm-e2e/SKILL.md
```

### CSV schema (MCP)

File: `tests/csv/login.csv`

| Column        | Description |
|---------------|-------------|
| `test_id`     | Groups steps into one test case |
| `test_name`   | Human-readable test name |
| `step`        | Step order within the test |
| `action`      | Action keyword |
| `locator_key` | Key from `tests/locators/login.json` |
| `input`       | URL, text, browser name, etc. |
| `expected`    | Assertion target |
| `notes`       | Optional description |

## Test coverage

| Test ID       | Scenario |
|---------------|----------|
| TC_LOGIN_001  | Valid login → dashboard |
| TC_LOGIN_002  | Invalid credentials → error message |
| TC_LOGIN_003  | Empty username → required validation |
| TC_LOGIN_004  | Empty password → required validation |
| TC_LOGIN_005  | Both fields empty → required validation |
| TC_LOGIN_006  | UI smoke — heading, button, forgot password, branding |

## Adding new tests

### Java POM

1. Add locators/methods to `LoginPage.java` or new page objects.
2. Add a row to `src/test/resources/login-test-data.csv`.
3. Handle the new `scenario` in `LoginTest.java` if needed.
4. Run `mvn test`.

### MCP agent

1. Add locators to `tests/locators/login.json`.
2. Append rows to `tests/csv/login.csv` with a new `test_id`.
3. Each test must start with `start_browser` and end with `close_browser`.
4. Ask the agent to run the new test case.

## Data file relationship

| File | Purpose |
|------|---------|
| `tests/csv/login.csv` | Step-level instructions for MCP agent execution |
| `src/test/resources/login-test-data.csv` | Inputs and expectations for Java TestNG tests |
| `tests/locators/login.json` | Locators for MCP; Java locators live in page object classes |

## Limitations

- MCP tests run inside Cursor — not standalone without an agent.
- Java tests require Chrome and network access to the public demo site.
- The demo site may be slow or rate-limited; retries may be needed.
- Locators may drift when OrangeHRM updates its UI.
