package utils.reporting;

import java.util.ArrayList;

public class FinishedSuiteReport {
    private String projectName;
    private ArrayList<String> additionalInfo;
    private String failedTests;

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setAdditionalInfo(ArrayList<String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setFailedTests(String failedTests) {
        this.failedTests = failedTests;
    }

    @Override
    public String toString() {
        return "FinishedSuite{" +
                "projectName='" + projectName + '\'' +
                ", additionalInfo=" + additionalInfo +
                ", failedTests='" + failedTests + '\'' +
                '}';
    }
}
