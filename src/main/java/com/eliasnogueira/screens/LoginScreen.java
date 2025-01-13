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
package com.eliasnogueira.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginScreen {

    @AndroidFindBy(accessibility = "input-email")
    @iOSXCUITFindBy(accessibility = "input-email")
    WebElement email;

    @AndroidFindBy(accessibility = "input-password")
    @iOSXCUITFindBy(accessibility = "input-password")
    WebElement password;

    @AndroidFindBy(accessibility = "button-LOGIN")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name == \"LOGIN\"`][2]")
    WebElement loginButton;

    @AndroidFindBy(id = "android:id/message")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[2]")
    WebElement alertMessage;

    @AndroidFindBy(id = "android:id/button1")
    @iOSXCUITFindBy(accessibility = "OK")
    WebElement alertButton;

    public LoginScreen(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void login(String email, String password) {
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        this.loginButton.click();
    }

    public String retrieveAlertMessage() {
        return alertMessage.getText();
    }

    public void tapOnOK() {
        alertButton.click();
    }
}
