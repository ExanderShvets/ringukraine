package utils.reporting;

import java.util.ArrayList;

public class FinishedSuiteReport {
    private String projectName;
    private String area;
    private ArrayList<String> additionalInfo;
    private String failedTests;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ArrayList<String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(ArrayList<String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getFailedTests() {
        return failedTests;
    }

    public void setFailedTests(String failedTests) {
        this.failedTests = failedTests;
    }

    public String getPcName() {
        String pcName = "";
        for (String item : additionalInfo) {
            if (item.startsWith("PC name")) {
                pcName = item.split(":")[1].trim();
            }
        }
        return pcName;
    }

    @Override
    public String toString() {
        return "FinishedSuite{" +
                "projectName='" + projectName + '\'' +
                ", area='" + area + '\'' +
                ", additionalInfo=" + additionalInfo +
                ", failedTests='" + failedTests + '\'' +
                '}';
    }
}
