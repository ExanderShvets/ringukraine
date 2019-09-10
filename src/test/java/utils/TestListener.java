package utils;

import org.apache.commons.io.FileUtils;
import org.deepsymmetry.GifSequenceWriter;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import utils.logs.LogForTest;
import utils.reporting.FailedTestEvent;
import utils.reporting.FailedTestReport;
import utils.reporting.PostSender;
import utils.textonimage.TextOnImage;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import static base.tests.BasicTest.*;
import static base.tests.BasicTest.analyzeLog;
import static utils.DriverListener.*;
import static utils.logs.LogForTest.*;

@SuppressWarnings({"SpellCheckingInspection"})
public class TestListener extends TestListenerAdapter {
    public static String PC_NAME = "";
    public static String TEST_CLASS = "";
    public static String TEST_LOCALE = "";
    public static String OS = "";
    public static String AVERAGE_TEST_DURATION;
    private static String TEST_GROUP = "";
    private static String TEST_NAME = "";
    private static String TEST_PARAMETERS = "";
    private static ArrayList<File> screenNamesList = new ArrayList<>();
    private static String date = "";
    private static String failedTestsForEmail = "";
    private String currentURL;

    @SuppressWarnings("unused")
    public static String refactorForWords(String words) {
        StringBuilder wordsForRefactor = new StringBuilder();
        ArrayList<Character> testNameArr = new ArrayList<>();
        char[] testNameCharArray = words.toCharArray();
        for (char chars : testNameCharArray) {
            if (Character.isUpperCase(chars)) {
                testNameArr.add(' ');
                testNameArr.add(chars);
            } else testNameArr.add(chars);
        }
        for (Character ch : testNameArr) {
            wordsForRefactor.append(ch);
        }
        return wordsForRefactor.toString().toUpperCase().trim();
    }

    private static void screenForTestFailure() {
        String filepath;
        try {
            if (driver != null) {
                File scrFile = driver.getScreenshotAs(OutputType.FILE);
                date = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date());
                filepath = SCREEN_FOLDER + TEST_CLASS + File.separator
                        + date + "_" + TEST_NAME + "_LOCALE_" + TEST_LOCALE + ".png";
                FileUtils.copyFile(scrFile, new File(filepath));
                String textForScreen = date + "\n" + "TEST CLASS   : " + TEST_CLASS + "\nTEST NAME     : "
                        + TEST_NAME + "\nTEST LOCALE: " + TEST_LOCALE + "\nTEST GROUP  : " + TEST_GROUP + "\nINPUT PARAMETERS : " +
                        TEST_PARAMETERS + "\n" + LogForTest.getFullTestLog();
                TextOnImage.addTextToImageAndSave(textForScreen, filepath);
                screenNamesList.add(new File(filepath));
            }
        } catch (IOException e) {
            LogForTest.LOGGER.error("Can't create screen for test failure, occur error", e);
        }
    }

    public static String getFailedTestsForEmail() {
        return failedTestsForEmail;
    }

    private static String trimString(String stringToTrim) {
        return stringToTrim.length() > 20 ? stringToTrim.substring(0, 20) : stringToTrim;
    }

    private void writeErrorTraceToFile() {
        try {
            if (errorTrace.size() != 0) {
                File path = new File(TMP_FOLDER);
                if (!path.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    path.mkdir();
                }
                File file = new File(TMP_FOLDER + date + TEST_NAME + ".txt");
                PrintWriter writer = new PrintWriter(file);
                for (String line : errorTrace) {
                    writer.print(System.lineSeparator());
                    writer.print(line);
                }
                screenNamesList.add(file);
                writer.close();
            }
        } catch (IOException e) {
            LogForTest.LOGGER.error("Can't create error stack trace file, occur error", e);
        }
    }

    @Override
    public void onTestStart(ITestResult tr) {
        resetLogLists();
        testStepCount = 1;

        /*INIT TEST HEADER VARIABLES*/
        TEST_GROUP = (tr.getMethod().getGroups() == null || tr.getMethod().getGroups().length == 0) ? "" : tr.getMethod().getGroups()[0];
        TEST_PARAMETERS = Arrays.toString(tr.getParameters());
        TEST_CLASS = tr.getTestClass().getRealClass().getSimpleName();
        TEST_NAME = "[" + tr.getName() + "]";
        OS = getOS();
        PC_NAME = getPcName();
        TEST_LOCALE = (BASE_URL.equals("https://en-uk.ring.com/"))
                ? "en-uk.ring" : "[" + getLocale() + "]";

        /*PRINT TEST HEADER*/
        LogForTest.header("Date: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        LogForTest.header("Browser: " + BROWSER_VERSION);
        LogForTest.header("PC name: " + PC_NAME);
        LogForTest.header("OS: " + OS);
        LogForTest.header("Class: " + TEST_CLASS);
        LogForTest.header("Test group: " + TEST_GROUP);
        LogForTest.header("Test: " + TEST_NAME);
        LogForTest.header("Locale: " + TEST_LOCALE);
        LogForTest.header("Parameters: " + TEST_PARAMETERS);
        super.onTestStart(tr);
    }

    private void saveImage() throws IOException {
        if (TEST_CLASS.equals("WebcloudCheckTest")) {
            return;
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date());
        String fileDirectory = SCREEN_FOLDER + TEST_CLASS + File.separator;
        FileUtils.forceMkdir(new File(fileDirectory));
        String filePath = fileDirectory + date + "_" + TEST_NAME + "_LOCALE_" + TEST_LOCALE + ".gif";
        BufferedImage[] bufferedImages = new BufferedImage[animationImage.size()];
        TextOnImage.addTextToImageAndSave("Start of gif", animationImage.get(0).getAbsolutePath());
        TextOnImage.addTextToImageAndSave("End of gif", animationImage.get(animationImage.size() - 1).getAbsolutePath());
        for (int x = 0; x < animationImage.size(); x++) {
            bufferedImages[x] = ImageIO.read(animationImage.get(x));
        }
        ImageOutputStream output =
                new FileImageOutputStream(
                        new File(filePath));
        GifSequenceWriter writer = new GifSequenceWriter(output,
                BufferedImage.TYPE_INT_RGB, 1000, true);
        for (BufferedImage img : bufferedImages) {
            writer.writeToSequence(img);
        }
        writer.close();
        screenNamesList.add(new File(filePath));
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        passedTests++;
        LogForTest.info("Test \"" + tr.getName() + "\" successfully passed");
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        skippedTests++;
        super.onTestSkipped(tr);
        LogForTest.info("Test \"" + tr.getName() + "\" skipped");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        failedTests++;
        currentURL = driver == null ? "Empty value" : driver.getCurrentUrl();

        errorTrace.addAll(analyzeLog());
        animationImage.add(driver.getScreenshotAs(OutputType.FILE));
        try {
            saveImage();
        } catch (IOException e) {
            LogForTest.LOGGER.error("Occur error while saving image", e);
        } catch (OutOfMemoryError e) {
            LogForTest.info("Unavailable to save too big .gif animation. OutOfMemoryError!");
        }
        LogForTest.error(String.valueOf(tr.getThrowable().getMessage()));
        if (!TEST_CLASS.equals("WebcloudCheckTest")) {
            screenForTestFailure();
        }

        /*
         * Send POST Request for ADD RESULTS TO DB if it's selenium server,
         * Send POST request for send email if it's dev machine
         */

        if ((getPcName().contains("selenium"))
                && !"WebcloudCheckTest".equals(TEST_CLASS)) {
            addEventToDB();
        } else {
            System.out.println("Email was send to dev email");
            sendDevEmail();
        }

        writeErrorTraceToFile();
        super.onTestFailure(tr);

        screenNamesList = new ArrayList<>();
        errorTrace = new ArrayList<>();
        for (File anim : animationImage) {
            try {
                FileUtils.forceDelete(anim);
            } catch (IOException e) {
                LogForTest.LOGGER.error("Can't delete temp files (used for creating animation), occur error", e);
            }
        }
    }

    private void addEventToDB() {
        FailedTestEvent failedTest = new FailedTestEvent();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        failedTest.setTestName(TEST_NAME);
        failedTest.setClassName(TEST_CLASS);

        if (TEST_GROUP != null && !TEST_GROUP.equals("")) {
            failedTest.setGroupName(TEST_GROUP
                    .replace("[", "")
                    .replace("]", ""));
        } else {
            failedTest.setGroupName("NO GROUP");
        }
        failedTest.setTestIdValue("NO TEST ID");

        failedTest.setPcName(PC_NAME);
        failedTest.setPcOs(OS);
        failedTest.setBrowser(BROWSER_VERSION);
        failedTest.setLocale(TEST_LOCALE);
        failedTest.setDate(dateFormat.format(new Date()));
        failedTest.setParams(TEST_PARAMETERS);
        failedTest.setUrl(currentURL);
        failedTest.setWebSite("ring-ukraine");
        StringBuilder steps = new StringBuilder();
        getInfoLog().forEach(steps::append);
        failedTest.setSteps(steps.toString());
        failedTest.setCausedBy(LogForTest.getErrorLog().toString());

        if (TEST_CLASS.equals("WebcloudCheckTest")) {
            failedTest.setPngImage("not found");
            failedTest.setGifImage("not found");
        } else {
            ArrayList<String> tempFilePaths = new ArrayList<>();
            screenNamesList.forEach(file -> tempFilePaths.add(file.getAbsolutePath()));
            if (tempFilePaths.get(0).endsWith("png")) {
                failedTest.setPngImage(tempFilePaths.get(0));
                failedTest.setGifImage(tempFilePaths.get(1));
            } else {
                failedTest.setPngImage(tempFilePaths.get(1));
                failedTest.setGifImage(tempFilePaths.get(0));
            }
        }

        if (getPcName().equalsIgnoreCase("QA-ALEKSANDR-S")) {
            sendDevEmail();
        }

        new PostSender().sendPost(failedTest);
    }

    private void sendDevEmail() {
        FailedTestReport testReport = new FailedTestReport();
        testReport.setBugUrl(driver.getCurrentUrl());
        testReport.setCausedBy(LogForTest.getErrorLog().toString());
        String projectName = "ring-ukraine";

        testReport.setProjectName(projectName);
        testReport.setAdditionalInfo(LogForTest.getHeaderLog());
        testReport.setSteps(LogForTest.getInfoLog());
        if (TEST_CLASS.toLowerCase().contains("members")) {
            testReport.setArea("Members");
        } else testReport.setArea("Front");

        if (TEST_CLASS.equals("WebcloudCheckTest")) {
            testReport.setProjectName("Webclouds - Google black list check");
            testReport.setArea("Webclouds");
        }

        ArrayList<String> filePaths = new ArrayList<>();
        screenNamesList.forEach(file -> filePaths.add(file.getAbsolutePath()));
        testReport.setFilePaths(filePaths);
        new PostSender().sendPost(testReport);
    }


    @Override
    public void onFinish(ITestContext testContext) {
        List<ITestResult> failedTestList = getFailedTests();
        Map<String, ArrayList<String>> failedTestMap = new HashMap<>();

        failedTestList.forEach(failedTest -> {
            String[] temp = failedTest.getMethod().getTestClass().getName().split("\\.");
            String testClass = temp[temp.length - 1];
            String testName = "<br>&nbsp;&nbsp;&nbsp;&nbsp;[" + failedTest.getName();
            String parameters = "";
            if (failedTest.getParameters() != null && failedTest.getParameters().length >= 1) {
                parameters = " - " + trimString(failedTest.getParameters()[0].toString());
            }

            if (failedTestMap.containsKey(testClass)) {
                failedTestMap.get(testClass).add(testName + parameters + "]");
            } else {
                String finalParameters = parameters;
                failedTestMap.put(testClass, new ArrayList<String>() {{
                    add(testName + finalParameters + "]");
                }});
            }
        });

        StringBuilder failedTestStringBuilder = new StringBuilder();
        failedTestMap.forEach((key, value) -> {
            failedTestStringBuilder.append("<strong>").append(key).append("</strong>");
            value.forEach(failedTestStringBuilder::append);
        });
        if (!failedTestStringBuilder.toString().isEmpty() && !failedTestStringBuilder.toString().equals("")) {
            failedTestsForEmail = failedTestsForEmail + "<br>" + failedTestStringBuilder.toString();
        }
        failedTestList.addAll(getPassedTests());
        AVERAGE_TEST_DURATION = getLongAsTime((long)
                failedTestList.stream()
                        .mapToLong(test -> (test.getEndMillis() - test.getStartMillis()))
                        .average().orElse(0.0));

        super.onFinish(testContext);
    }

    private String getLongAsTime(long timeDiff) {
        long diffSeconds = timeDiff / 1000 % 60;
        long diffMinutes = timeDiff / (60 * 1000) % 60;
        long diffHours = timeDiff / (60 * 60 * 1000) % 24;
        return (diffHours == 0 ? "" : diffHours + "h ") +
                (diffMinutes == 0 ? "" : diffMinutes + "m ") +
                (diffSeconds == 0 ? "" : diffSeconds + "s ");
    }
}
