package utils.reporting;

import java.util.ArrayList;

public class FailedTestReport {
    private String projectName;
    private String area;
    private ArrayList<String> additionalInfo; // Test header: browser, class... etc
    private String bugUrl;
    private ArrayList<String> steps;
    private String causedBy;

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAdditionalInfo(ArrayList<String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setBugUrl(String bugUrl) {
        this.bugUrl = bugUrl;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public void setCausedBy(String causedBy) {
        this.causedBy = causedBy;
    }

    public void setFilePaths(ArrayList<String> filePaths) {
    }

    @Override
    public String toString() {
        return "FailedTest{" +
                "projectName='" + projectName + '\'' +
                ", area='" + area + '\'' +
                ", additionalInfo=" + additionalInfo +
                ", bugUrl='" + bugUrl + '\'' +
                ", steps=" + steps +
                ", causedBy='" + causedBy + '\'' +
                '}';
    }
}
