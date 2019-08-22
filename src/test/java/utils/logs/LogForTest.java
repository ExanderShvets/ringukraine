package utils.logs;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import java.util.ArrayList;

import static base.tests.BasicTest.testStepCount;

public class LogForTest {
    public static final Logger LOGGER = Logger.getLogger(LogForTest.class);
    private static final String INFO_LOG = "INFO: %s";
    private static final String ERROR_LOG = "ERROR: %s !";

    private static ArrayList<String> errorLog;
    private static ArrayList<String> infoLog;
    private static ArrayList<String> headerLog;

    public static void resetLogLists() {
        headerLog = new ArrayList<>();
        infoLog = new ArrayList<>();
        errorLog = new ArrayList<>();
    }

    public static void turnOffLogger() {
        LOGGER.setLevel(Level.OFF);
    }

    public static void turnOnLogger() {
        LOGGER.setLevel(Level.ALL);
    }

    public static ArrayList<String> getErrorLog() {
        return errorLog;
    }

    public static ArrayList<String> getInfoLog() {
        return infoLog;
    }

    public static ArrayList<String> getHeaderLog() {
        return headerLog;
    }

    public static String header(String message) {
        LOGGER.info(String.format(INFO_LOG, message));
        Reporter.log(String.format(INFO_LOG, message));
        headerLog.add(message + "\n");
        return String.format(INFO_LOG, message);
    }

    public static String info(String message) {
        LOGGER.info(String.format(INFO_LOG, testStepCount + ") " + message));
        Reporter.log(String.format(INFO_LOG, testStepCount + ") " + message));
        infoLog.add(testStepCount + ") " + message + "\n");
        testStepCount++;
        return String.format(INFO_LOG, testStepCount + ") " + message);
    }

    public static String error(String message) {
        LOGGER.error(String.format(ERROR_LOG, message));
        Reporter.log(String.format(ERROR_LOG, message));
        errorLog.add(message + "\n");
        return String.format(ERROR_LOG, message);
    }

    public static String getFullTestLog() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Additional info\n");
        headerLog.forEach(stringBuilder::append);
        stringBuilder.append("Steps to reproduce:\n");
        infoLog.forEach(stringBuilder::append);
        stringBuilder.append("What went wrong? (Screenshot(s) in attachment):\n");
        errorLog.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
