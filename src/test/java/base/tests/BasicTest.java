package base.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DriverListener;
import utils.TestListener;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Listeners;
import utils.logs.LogForTest;
import utils.reporting.FinishedSuiteReport;
import utils.reporting.PostSender;
import utils.PropertyLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static utils.DriverListener.animationImage;
import static utils.TestListener.*;
import static utils.TestListener.TEST_CLASS;


@Listeners({TestListener.class, utils.verify.TestMethodListener.class})
public abstract class BasicTest {
    public static int testStepCount = 1;
    public static String BROWSER_VERSION;
    public static int passedTests = 0;
    public static int failedTests = 0;
    public static int skippedTests = 0;
    public static EventFiringWebDriver driver;
    public final static String SCREEN_FOLDER;
    public final static String TMP_FOLDER;
    protected final static String DRIVER_FOLDER;
    private static final Date START_DATE = new Date();
    public static String BASE_URL = PropertyLoader.loadProperty("base.URL");

    static {
        LogForTest.LOGGER.info("SERVICE MESSAGE: Initializing properties for Windows");
        SCREEN_FOLDER = PropertyLoader.loadProperty("windowsScreenFolder");
        DRIVER_FOLDER = PropertyLoader.loadProperty("windowsDriverFolder");
        TMP_FOLDER = PropertyLoader.loadProperty("windowsTmpFolder");
    }

    public static ArrayList<String> analyzeLog() {
        ArrayList<String> jsErrors = new ArrayList<>();
        if (driver != null) {
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logEntries) {
                jsErrors.add("CONSOLE LOG : " + entry.getLevel() + "  " + entry.getMessage());
            }
        }
        return jsErrors;
    }

    public static String getLocale() {
        if (BASE_URL.equals("https://ring-ukraine.com")) {
            return "https://ring-ukraine.com";
        }
        String[] s = BASE_URL.split(".com");
        return s[s.length - 1].replaceAll("/", "").toUpperCase();
    }

    public abstract void initPages();

    @BeforeMethod
    public void initTest() {
        testStepCount = 1;
        animationImage = new ArrayList<>();
    }


    @BeforeSuite
    public void initEnvironment() {
        LogForTest.LOGGER.info("SERVICE MESSAGE: Set driver folder property");
        System.setProperty("webdriver.chrome.driver", DRIVER_FOLDER); //Chrome driver windows

        LogForTest.LOGGER.info("SERVICE MESSAGE: Set driver options");
        String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64; Dreamscape/1.0;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36";
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--user-agent=" + userAgent);
        co.addArguments("--window-size=1920,1080");
        co.addArguments("--headless");
        co.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        co.addArguments("--no-sandbox"); // Bypass OS security model
        co.setExperimentalOption("w3c", false);

        LogForTest.LOGGER.info("SERVICE MESSAGE: Set driver Desired capabilities");
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, co);
        LogForTest.LOGGER.info("SERVICE MESSAGE: Switch on driver Logging, set levels");
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        LogForTest.LOGGER.info("SERVICE MESSAGE: Init driver");
        WebDriver webDriver = new ChromeDriver(cap);
        Capabilities caps = ((RemoteWebDriver) webDriver).getCapabilities();
        BROWSER_VERSION = caps.getBrowserName() + ", version " + caps.getVersion();
        driver = new EventFiringWebDriver(webDriver);
        driver.register(new DriverListener("#FFFF00 ", 1, 1, TimeUnit.MILLISECONDS));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @AfterSuite(alwaysRun = true)
    public void closeBrowser() {
        Date endDate = new Date();

        double passed = passedTests;
        double doneTests = failedTests + passed;
        double rate = ((int) (passed / doneTests * 10000)) / 100.0;

        FinishedSuiteReport suiteReport = new FinishedSuiteReport();
        ArrayList<String> additionalInfo = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        additionalInfo.add("Date:" + dateFormat.format(new Date()));
        additionalInfo.add("Browser: " + BROWSER_VERSION);
        additionalInfo.add("PC name: " + PC_NAME);
        additionalInfo.add("OS: " + OS);
        additionalInfo.add("Locale: " + TEST_LOCALE);
        additionalInfo.add("Tests: passed= " + passedTests + "; failed= " + failedTests + "; skipped= " + skippedTests);
        additionalInfo.add("Success rate: " + rate + "%");
        additionalInfo.add("Started: " + dateFormat.format(START_DATE));
        additionalInfo.add("Finished: " + dateFormat.format(new Date()));
        additionalInfo.add("Total time: " + getTimeDiff(START_DATE, endDate));
        additionalInfo.add("Average test duration:" + AVERAGE_TEST_DURATION);

        String projectName = "RING-UKRAINE SELENIUM";
        suiteReport.setProjectName(projectName);
        suiteReport.setAdditionalInfo(additionalInfo);
        suiteReport.setFailedTests(TestListener.getFailedTestsForEmail());
        if (driver != null)
            driver.quit();
    }

    private String getTimeDiff(Date dateOne, Date dateTwo) {
        long diff = dateTwo.getTime() - dateOne.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        return diffHours + "h " + diffMinutes + "m " + diffSeconds + "s";
    }

    public void gotoPage(String url) {
        if (driver == null) {
            initEnvironment();
        }
        try {
            String currentUrl = driver.getCurrentUrl().split("#")[0];
            if (!driver.getCurrentUrl().split("#")[0].equals(url)) {
                driver.get(url);
                Thread.sleep(2000);
                if (driver.getCurrentUrl().split("#")[0].equals(currentUrl)) {
                    driver.get(url);
                }
            } else {
                LogForTest.info("Already on " + url);

            }
            initPages();
        } catch (WebDriverException e) {
            Assert.assertTrue(false, "Timeout loading page");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitForJSandJQueryToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 6);
        try {
            wait.until(driver ->
                    (((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete"))
                            && (((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0)));
        } catch (TimeoutException e) {
            System.out.println("");
        }
    }
}
