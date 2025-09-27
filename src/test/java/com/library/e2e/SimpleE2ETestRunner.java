package com.library.e2e;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple E2E Test Runner
 * 
 * This class provides a simple way to run E2E tests against a running application.
 * Make sure the application is running on localhost:8080 before running these tests.
 */
public class SimpleE2ETestRunner {

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected int port = 8080;

    @BeforeAll
    static void setUpPlaywright() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false) // Set to true for CI/CD
                .setSlowMo(100)); // Slow down for better visibility
    }

    @AfterAll
    static void tearDownPlaywright() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeEach
    void setUp() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720));
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }

    protected void navigateTo(String path) {
        page.navigate(getBaseUrl() + path);
        page.waitForLoadState();
    }

    protected void waitForElement(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(60000));
    }

    protected void clickElement(String selector) {
        page.click(selector, new Page.ClickOptions().setTimeout(60000));
    }

    protected void fillInput(String selector, String value) {
        page.fill(selector, value);
    }

    protected String getText(String selector) {
        return page.textContent(selector);
    }

    protected void assertElementVisible(String selector) {
        assertTrue(page.isVisible(selector), "Element should be visible: " + selector);
    }

    protected void assertElementNotVisible(String selector) {
        assertFalse(page.isVisible(selector), "Element should not be visible: " + selector);
    }

    protected void assertTextContains(String selector, String expectedText) {
        String actualText = getText(selector);
        assertTrue(actualText.contains(expectedText), 
                "Expected text '" + expectedText + "' not found in element '" + selector + "'. Actual: " + actualText);
    }

    protected void assertUrlContains(String expectedPath) {
        String currentUrl = page.url();
        assertTrue(currentUrl.contains(expectedPath), 
                "Expected URL to contain '" + expectedPath + "', but was: " + currentUrl);
    }
}
