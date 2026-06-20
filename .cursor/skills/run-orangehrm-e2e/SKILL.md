---
name: run-orangehrm-e2e
description: Run CSV-driven E2E tests against OrangeHRM using user-selenium MCP. Use when asked to run login tests, execute CSV tests, or validate OrangeHRM E2E scenarios.
---

# Run OrangeHRM E2E Tests

Execute CSV test cases against the OrangeHRM demo site via the `user-selenium` MCP server.

## Prerequisites

- `user-selenium` MCP server enabled in Cursor
- Chrome installed locally
- Test files: `tests/csv/login.csv`, `tests/locators/login.json`

## Execution workflow

1. Read `tests/csv/login.csv` and group rows by `test_id`, ordered by `step`.
2. Load locators from `tests/locators/login.json`.
3. Create `tests/results/` if it does not exist (for failure screenshots).
4. For each test group, execute steps sequentially.
5. Always run `close_browser` even when earlier steps fail.
6. After all tests, print a results table:

```
| test_id | test_name | status | failed_step | reason |
```

## Locator resolution

- `locator_key` maps to an entry in `login.json` (e.g. `username` → `{ "by": "name", "value": "username" }`).
- Skip locator lookup for browser-level actions (`start_browser`, `navigate`, `assert_url`, `close_browser`).

## Action → MCP tool mapping

| CSV `action` | MCP tool | Arguments |
|--------------|----------|-----------|
| `start_browser` | `start_browser` | `{ "browser": "<input>", "options": { "headless": false } }` |
| `navigate` | `navigate` | `{ "url": "<input>" }` |
| `type` | `send_keys` | `{ "by": "<locator.by>", "value": "<locator.value>", "text": "<input>", "timeout": 10000 }` |
| `click` | `interact` | `{ "action": "click", "by": "<locator.by>", "value": "<locator.value>", "timeout": 10000 }` |
| `assert_text` | `get_element_text` | `{ "by": "<locator.by>", "value": "<locator.value>", "timeout": 10000 }` — pass if result contains `expected` |
| `assert_url` | `execute_script` | `{ "script": "return window.location.href" }` — pass if result contains `expected` |
| `assert_attribute` | `get_element_attribute` | `{ "by": "<locator.by>", "value": "<locator.value>", "attribute": "<attr from expected>", "timeout": 10000 }` — `expected` format is `attr=value` |
| `snapshot` | Fetch MCP resource | `accessibility://current` on server `user-selenium` |
| `screenshot` | `take_screenshot` | `{ "outputPath": "tests/results/<test_id>-step<step>-fail.png" }` |
| `close_browser` | `close_session` | `{}` |

Read MCP tool schemas from the `user-selenium` MCP server before calling tools if unsure of parameters.

## Failure handling

1. On assertion or interaction failure: call `take_screenshot` with path `tests/results/{test_id}-step{step}-fail.png`.
2. Record `failed_step` and `reason` in the results table.
3. Continue remaining steps in the current test (including `close_browser`).
4. Proceed to the next `test_id`.

## Locator fallback

If `send_keys`, `interact`, or `get_element_text` times out:

1. Fetch `accessibility://current` from `user-selenium`.
2. Identify the correct element from the accessibility tree.
3. Retry with an updated locator; if successful, update `tests/locators/login.json`.

## Default timeouts

Use `timeout: 10000` (10 seconds) on all element interactions and assertions unless the demo site is unusually slow.

## Running tests

When the user asks to run tests, execute all rows in `tests/csv/login.csv` unless they specify a subset (e.g. `TC_LOGIN_001` only).

Example prompt: *"Run OrangeHRM login E2E tests from CSV"*

## Demo credentials

- URL: `https://opensource-demo.orangehrmlive.com/web/index.php/auth/login`
- Username: `Admin`
- Password: `admin123`
