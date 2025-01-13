/*
 * MIT License
 *
 * Copyright (c) 2025 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.eliasnogueira.basic;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

import static com.eliasnogueira.config.ConfigurationManager.configuration;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AndroidTest {

    private static AppiumDriver driver;

    @BeforeAll
    static void setup() throws MalformedURLException {
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options.setApp(System.getProperty("user.dir") + configuration().androidAppPath());
        uiAutomator2Options.setDeviceName(configuration().androidDeviceName());
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setPlatformVersion(configuration().androidPlatformVersion());
        uiAutomator2Options.setAvd(configuration().androidDeviceName());

        driver = new AndroidDriver(URI.create(configuration().appiumUrl()).toURL(), uiAutomator2Options);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void login() {
        // access login
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("Login")).click();

        // login
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("input-email")).sendKeys("elias@elias.com");
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("input-password")).sendKeys("1q2w3e4r5t");
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("button-LOGIN")).click();

        // wait for the popup
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By messageId = AppiumBy.id("android:id/message");
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageId));

        // text validation
        String expectedText = driver.findElement(messageId).getText();
        assertEquals("You are logged in!", expectedText);
        driver.findElement(AppiumBy.id("android:id/button1")).click();
    }
}
