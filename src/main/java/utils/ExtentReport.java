package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

public class ExtentReport {
    public static ExtentReports extentreport = null;
    public static ExtentTest extentlog;

    public static void initialize(String extentConfigXmlpath) {

        if (extentreport == null) {

            extentreport = new ExtentReports(extentConfigXmlpath, true);

            extentreport.addSystemInfo("Host Name", System.getProperty("user.name"));

            extentreport.addSystemInfo("Environment", "QA");

            extentreport.addSystemInfo("OS", "Mac OS X");

            extentreport.loadConfig(new File(System.getProperty("user.dir") + "/resources/extent-config.xml"));

        }
    }
}
