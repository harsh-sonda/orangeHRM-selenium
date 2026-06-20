package com.orangehrm.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Prints test progress to the console as each test starts and finishes.
 */
public class RealTimeTestListener implements ITestListener {

    private final AtomicInteger completed = new AtomicInteger(0);
    private final Map<String, Long> startTimes = new ConcurrentHashMap<>();
    private int totalTests;

    @Override
    public void onStart(ITestContext context) {
        totalTests = context.getAllTestMethods().length;
        System.out.println();
        System.out.println("=== OrangeHRM E2E: " + totalTests + " tests ===");
        System.out.println();
    }

    @Override
    public void onTestStart(ITestResult result) {
        startTimes.put(resultKey(result), System.currentTimeMillis());
        System.out.printf("▶ START  [%d/%d] %s — %s%n",
                completed.get() + 1,
                totalTests > 0 ? totalTests : completed.get() + 1,
                testLabel(result),
                description(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        printFinished("PASS", result, null);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String reason = result.getThrowable() != null
                ? result.getThrowable().getMessage()
                : "unknown error";
        printFinished("FAIL", result, reason);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String reason = result.getThrowable() != null
                ? result.getThrowable().getMessage()
                : "skipped";
        printFinished("SKIP", result, reason);
    }

    @Override
    public void onFinish(ITestContext context) {
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        System.out.println();
        System.out.printf("=== Done: %d passed, %d failed, %d skipped (of %d) ===%n",
                passed, failed, skipped, totalTests);
        System.out.println();
    }

    private void printFinished(String status, ITestResult result, String reason) {
        int index = completed.incrementAndGet();
        long elapsedMs = elapsedMs(result);
        System.out.printf("%s  [%d/%d] %s (%.1fs)%n",
                statusMarker(status),
                index,
                totalTests > 0 ? totalTests : index,
                testLabel(result),
                elapsedMs / 1000.0);
        if (reason != null && !reason.isBlank()) {
            System.out.println("         ↳ " + reason);
        }
    }

    private long elapsedMs(ITestResult result) {
        Long start = startTimes.remove(resultKey(result));
        if (start == null) {
            return result.getEndMillis() - result.getStartMillis();
        }
        return System.currentTimeMillis() - start;
    }

    private static String statusMarker(String status) {
        return switch (status) {
            case "PASS" -> "✓ PASS";
            case "FAIL" -> "✗ FAIL";
            case "SKIP" -> "○ SKIP";
            default -> status;
        };
    }

    private static String testLabel(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName()
                + "." + result.getMethod().getMethodName();
    }

    private static String description(ITestResult result) {
        String description = result.getMethod().getDescription();
        return description != null && !description.isBlank() ? description : testLabel(result);
    }

    private static String resultKey(ITestResult result) {
        return result.getTestClass().getName() + "#" + result.getMethod().getMethodName()
                + "@" + result.getInstance().hashCode();
    }
}
