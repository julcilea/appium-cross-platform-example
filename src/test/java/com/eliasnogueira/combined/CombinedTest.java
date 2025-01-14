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
package com.eliasnogueira.combined;

import com.eliasnogueira.driver.DriverFactory;
import com.eliasnogueira.screens.LoginScreen;
import com.eliasnogueira.screens.MainScreen;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.eliasnogueira.config.ConfigurationManager.configuration;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CombinedTest {

    private static AppiumDriver driver;

    @BeforeAll
    static void setUp() throws Exception {
        driver = DriverFactory.valueOf(configuration().platform().toUpperCase()).createDriver();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void successfulLogin() {
        MainScreen mainScreen = new MainScreen(driver);
        mainScreen.tagOnLogin();

        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.login("elias@elias.com", "12w3e4r5t");

        assertEquals("You are logged in!", loginScreen.retrieveAlertMessage());

        loginScreen.tapOnOK();
    }
}
