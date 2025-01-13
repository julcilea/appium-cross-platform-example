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
package com.eliasnogueira.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URI;

import static com.eliasnogueira.config.ConfigurationManager.configuration;

public enum DriverFactory {

    ANDROID {
        @Override
        public AppiumDriver createDriver() throws MalformedURLException {
            var uiAutomator2Options = new UiAutomator2Options();
            uiAutomator2Options.setApp(System.getProperty("user.dir") + configuration().androidAppPath());
            uiAutomator2Options.setDeviceName(configuration().androidDeviceName());
            uiAutomator2Options.setPlatformName("Android");
            uiAutomator2Options.setPlatformVersion(configuration().androidPlatformVersion());
            uiAutomator2Options.setAvd(configuration().androidDeviceName());

            return new AndroidDriver(URI.create(configuration().appiumUrl()).toURL(), uiAutomator2Options);
        }
    }, IOS {
        @Override
        public AppiumDriver createDriver() throws MalformedURLException {
            var xcuiTestOptions = new XCUITestOptions();
            xcuiTestOptions.setApp(System.getProperty("user.dir") + configuration().iosAppPath());
            xcuiTestOptions.setDeviceName(configuration().iosDeviceName());
            xcuiTestOptions.setPlatformName("iOS");
            xcuiTestOptions.setPlatformVersion(configuration().iosPlatformVersion());

            return new IOSDriver(URI.create(configuration().appiumUrl()).toURL(), xcuiTestOptions);
        }
    };

    public abstract AppiumDriver createDriver() throws MalformedURLException;
}
