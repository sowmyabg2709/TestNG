package com.qa.opencart.listeners;

import java.util.Calendar;
import java.util.Date;
import java.util.Arrays;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestResult;
import org.testng.ITestContext;
import java.nio.file.Path;
import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;
import org.testng.ITestListener;
import com.qa.opencart.factory.DriverFactory;

public class ExtentReportListener extends DriverFactory implements ITestListener
{
    private static final String OUTPUT_FOLDER = "./build/";
    private static final String FILE_NAME = "TestExecutionReport.html";
    private static ExtentReports extent;
    public static ThreadLocal<ExtentTest> test;
    
    static {
        ExtentReportListener.extent = init();
        ExtentReportListener.test = new ThreadLocal<ExtentTest>();
    }
    
    private static ExtentReports init() {
        final Path path = Paths.get("./build/", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            try {
                Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        final ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("./build/TestExecutionReport.html");
        htmlReporter.config().setDocumentTitle("Automation Test Results");
        htmlReporter.config().setReportName("Automation Test Results");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        (ExtentReportListener.extent = new ExtentReports()).attachReporter(new ExtentReporter[] { (ExtentReporter)htmlReporter });
        ExtentReportListener.extent.setReportUsesManualConfiguration(true);
        return ExtentReportListener.extent;
    }
    
    public synchronized void onStart(final ITestContext context) {
        System.out.println("Test Suite started!");
    }
    
    public synchronized void onFinish(final ITestContext context) {
        System.out.println("Test Suite is ending!");
        ExtentReportListener.extent.flush();
        ExtentReportListener.test.remove();
    }
    
    public synchronized void onTestStart(final ITestResult result) {
        final String methodName = result.getMethod().getMethodName();
        final String qualifiedName = result.getMethod().getQualifiedName();
        final int last = qualifiedName.lastIndexOf(".");
        final int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        final String className = qualifiedName.substring(mid + 1, last);
        System.out.println(String.valueOf(methodName) + " started!");
        final ExtentTest extentTest = ExtentReportListener.extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        extentTest.assignCategory(new String[] { result.getTestContext().getSuite().getName() });
        extentTest.assignCategory(new String[] { className });
        ExtentReportListener.test.set(extentTest);
        ExtentReportListener.test.get().getModel().setStartTime(this.getTime(result.getStartMillis()));
    }
    
    public synchronized void onTestSuccess(final ITestResult result) {
        System.out.println(String.valueOf(result.getMethod().getMethodName()) + " passed!");
        ExtentReportListener.test.get().pass("Test passed");
        ExtentReportListener.test.get().getModel().setEndTime(this.getTime(result.getEndMillis()));
    }
    
    public synchronized void onTestFailure(final ITestResult result) {
        System.out.println(String.valueOf(result.getMethod().getMethodName()) + " failed!");
        try {
            ExtentReportListener.test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(this.getScreenshot()).build());
        }
        catch (IOException e) {
            System.err.println("Exception thrown while updating test fail status " + Arrays.toString(e.getStackTrace()));
        }
        ExtentReportListener.test.get().getModel().setEndTime(this.getTime(result.getEndMillis()));
    }
    
 
	public synchronized void onTestSkipped(final ITestResult result) {
        System.out.println(String.valueOf(result.getMethod().getMethodName()) + " skipped!");
        try {
            ExtentReportListener.test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(this.getScreenshot()).build());
        }
        catch (IOException e) {
            System.err.println("Exception thrown while updating test skip status " + Arrays.toString(e.getStackTrace()));
        }
        ExtentReportListener.test.get().getModel().setEndTime(this.getTime(result.getEndMillis()));
    }
    
    public synchronized void onTestFailedButWithinSuccessPercentage(final ITestResult result) {
        System.out.println("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName());
    }
    
    private Date getTime(final long millis) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}