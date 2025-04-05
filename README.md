# Appium Cross-Platform (Android, iOS) Example

Don't forget to give this project a ⭐

This project delivers a basic, but concise, example of a cross-platform test creation and execution using Appium and
Java by applying simple design patterns and best Appium features.

## Example

![Test execution](assets/test-execution.gif)

## Preconditions

* Java 23 (you might downgrade it without any problem)
* Install [Appium](https://appium.io/docs/en/latest/quickstart/install/)
* Install the [UiAutomator2 Driver](https://appium.io/docs/en/latest/quickstart/uiauto2-driver/)
    * Make sure you update it by running
      ```shell
      appium driver update uiautomator2
      ```
* Install the [XCUITest Driver](https://appium.github.io/appium-xcuitest-driver/latest/installation/)
    * Make sure you update it by running
        ```shell
        appium driver update xcuitest
        ```

## About the apps

The app used in this project is from the WebDriverIO native demo app: https://github.com/webdriverio/native-demo-app

## How to run

### Precondition

As a precondition you must run Appium in your local machine

```shell
appium
```

### Properties file

In the `config.properties` you will manage important data as:

* `platform`: indicates the platform the tests will run
* `appium.ip`: the IP address Appium is expected to run
* `appium.port`: the port address Appium is expected to run
* `device.ios.name`: the iPhone Simulator expected to run where you must have it created
* `device.android.name`: the Android Emulator expected to run where you must have it created
* `platform.ios.version`: the iOS version expected to run in the iPhone Simulator where you must have it created
* `platform.android.version`: the Android version expected to run in the Android Emulator where you must have it created
* `app.ios.path`: the path to the Android app (`.apk` file)
* `app.android.path`: the path to the iOS app (`.zip` or `.ipa` file)

**NOTE**

*The apps are located in the app folder and there is a concatenation in the `DriverFactory` to get its full path using
`System.getProperty("user.dir")`

### Running it

Change the `config.properties` file within the correct data you want to run based on the platform.

#### Example 1: Android

if you want to run the tests in the Android platform using _Android 13 (Tiramisu)_ in a existing emulator called
_TiramisuTest_, you might end up with the following information in the `config.properties` file

```properties
# all others not changed properties removed
platform=android
device.android.name=TiramisuTest
platform.android.version=13
```

#### Example 2: iOS

if you want to run the tests in the iOS platform using _iOS 18.2_ in an existing emulator called
_iPhone Simulator_, you might end up with the following information in the `config.properties` file

```properties
# all others not changed properties removed
platform=ios
device.android.name=iPhone Simulator
platform.android.version=18.2
```

## Code Explanation

### Configuration

The configuration, mostly based on the platform, is done by a property file located in the
`src/test/resources/config.properties`.

The code uses the value from each property through two classes. The `ConfigurationManager` is responsible to load the
configuration imitating a Singleton pattern using the `ConfigCache.getOrCreate()` method from
the [Owner library](https://matteobaccan.github.io/owner/).

The `Configuration` class is the one responsible to match each property in the `config.properties` file, enabling a
fluent way to get its data by associating the property name withing an attribute in the class by using the `@Key`
annotation

```java
// this will return the value from the device.android.name property
class ConfigExample {

    interface Configuration {
        @Key("device.android.name")
        String androidDeviceName();
    }

    class Usage {
        void main() {
            ConfigurationManager.configuration().androidDeviceName();
        }
    }
}
```

Please, note that the above code is an example that won't work by copy-past. It's just an education example. You must
rely on in the already created code.

### Driver Management

The basic driver information is done by the `DriverFactory` enumeration by setting all the necessary configurations to
run the tests using either Android or iOS.

Note that you can use any approach: `if-else`, `switch-case` or any that might work... This is a more elegant way to
implement the Factory pattern to execute the tests in the target platform.

You noticed that each enum will return an instance of the `AppiumDriver` for the specific platform. To use it in your
test you must use the `valueOf()` method from the enumeration and call the method associated with its creation which is
`createDriver()`

```java
class DriverExample {
    // gets the platform property value, set's it to upper case to match with the existing enums and call the createDriver()
    AppiumDriver driver = DriverFactory.valueOf(configuration().platform().toUpperCase()).createDriver();
}
```

### Page Objects

The `screens` package contains the Page Objects where two important things happens there.

First, the different annotation to locate the elements for Android or iOS which are `@AndroidFindBy` and
`iOSXCUITFindBy`, respectively. This will ensure you can use the same methods in the page object without duplications
where the only possible subject of change is the locator, where we annotate to have the correct one based on the target
platform.

```java
class PageObjectExample {
    @AndroidFindBy(id = "android:id/button1")
    @iOSXCUITFindBy(accessibility = "OK")
    WebElement alertButton;
}
```

Second, the constructor which will initialize the elements (based on the target platform). This is necessary to make
sure the element is instantiated using the correct locator value per platform. The code is simple

```java
class PageObjectExample {
    public PageObjectExample(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
```

### The test

The project shows two different examples: one per platform and one cross-platform

#### Per platform

Located in the `basic` package at the `src/test/java` it shows one test per platform by not using the `DriverFactory`.

Note that both the `AndroidTest` and `IOSTest` does the same thing, but the different (apart from the ugly code), is in
the locators. In a cross-platform test you would end up with a lot of code duplication, even test duplications.

#### Cross-Platform

To solve the above-mentioned problem we can make the usage of the Page Object approach using the specific locator
annotations to have a simple source of truth when interacting with the app. Note that this wouldn't be possible without
using Page Objects.

The combination of the `DriverFactory` with the created Page Objects will end up with a reliable ans elegant code that
will run in both platforms.

```java
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
    void testCalculateDefaultTip() {
        MainScreen mainScreen = new MainScreen(driver);
        mainScreen.tagOnLogin();

        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.login("elias@elias.com", "12w3e4r5t");

        assertEquals("You are logged in!", loginScreen.retrieveAlertMessage());

        loginScreen.tapOnOK();
    }
}
```

The value of the `platform` property will determine the platform where the test will be executed. In case of Android,
the page objects will use the locators set by the `@AndroidFindBy`, where in iOS it will use the `@iOSXCUITFindBy`.

test git.
