#!/usr/bin/env python3
"""Write Cypress-style pass/fail summary from Maven Surefire XML to GITHUB_STEP_SUMMARY."""

from __future__ import annotations

import os
import re
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

REPORTS_DIR = Path(os.environ.get("SUREFIRE_REPORTS_DIR", "target/surefire-reports"))
SUMMARY_FILE = os.environ.get("GITHUB_STEP_SUMMARY")


def short_test_name(raw: str) -> str:
    return re.split(r"\[E2eTestCase", raw, maxsplit=1)[0].strip()


def report_files() -> list[Path]:
    primary = REPORTS_DIR / "TEST-TestSuite.xml"
    if primary.exists():
        return [primary]
    return sorted(p for p in REPORTS_DIR.glob("TEST-*.xml") if p.parent == REPORTS_DIR)


def parse_reports() -> tuple[int, int, int, int, list[tuple[str, str, str]]]:
    """Return total, passed, failed, skipped, and failed test details."""
    xml_files = report_files()
    if not xml_files:
        raise FileNotFoundError(f"No Surefire reports found in {REPORTS_DIR}")

    total = passed = failed = skipped = 0
    failures: list[tuple[str, str, str]] = []

    for xml_file in xml_files:
        root = ET.parse(xml_file).getroot()
        if root.tag != "testsuite":
            continue

        total += int(root.attrib.get("tests", 0))
        failed += int(root.attrib.get("failures", 0))
        skipped += int(root.attrib.get("skipped", 0))
        failed += int(root.attrib.get("errors", 0))

        for case in root.findall("testcase"):
            name = short_test_name(case.attrib.get("name", "unknown"))
            failure = case.find("failure")
            error = case.find("error")
            if failure is not None:
                msg = (failure.text or failure.attrib.get("message", "")).strip().splitlines()[0][:200]
                failures.append((name, "failed", msg))
            elif error is not None:
                msg = (error.text or error.attrib.get("message", "")).strip().splitlines()[0][:200]
                failures.append((name, "error", msg))

    passed = max(total - failed - skipped, 0)
    return total, passed, failed, skipped, failures


def pass_rate(passed: int, total: int) -> str:
    if total == 0:
        return "0%"
    return f"{(passed / total) * 100:.1f}%"


def write_summary(total: int, passed: int, failed: int, skipped: int, failures: list) -> str:
    rate = pass_rate(passed, total)
    status = "✅ All tests passed" if failed == 0 and skipped == 0 else "❌ Tests failed"

    lines = [
        "## OrangeHRM E2E Test Summary",
        "",
        f"**{status}**",
        "",
        "| Metric | Count |",
        "|--------|------:|",
        f"| **Total** | {total} |",
        f"| **Passed** | {passed} |",
        f"| **Failed** | {failed} |",
        f"| **Skipped** | {skipped} |",
        f"| **Pass rate** | {rate} |",
        "",
    ]

    if failures:
        lines.extend(["### Failed tests", ""])
        for name, kind, message in failures:
            lines.append(f"- **{name}** ({kind}): {message}")
        lines.append("")

    lines.append("_Generated from Maven Surefire reports_")
    return "\n".join(lines) + "\n"


def main() -> int:
    try:
        total, passed, failed, skipped, failures = parse_reports()
    except FileNotFoundError as exc:
        print(str(exc), file=sys.stderr)
        return 1

    markdown = write_summary(total, passed, failed, skipped, failures)

    if SUMMARY_FILE:
        with open(SUMMARY_FILE, "a", encoding="utf-8") as handle:
            handle.write(markdown)
    else:
        print(markdown)

    print(f"Summary: total={total} passed={passed} failed={failed} skipped={skipped}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
