package utils.reporting;

import java.util.ArrayList;

public class FailedTestReport {
    private String projectName;
    private String area;
    private ArrayList<String> additionalInfo; // Test header: browser, class... etc
    private String bugUrl;
    private ArrayList<String> steps;
    private String causedBy;
    private ArrayList<String> filePaths;

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

    public String getBugUrl() {
        return bugUrl;
    }

    public void setBugUrl(String bugUrl) {
        this.bugUrl = bugUrl;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public String getCausedBy() {
        return causedBy;
    }

    public void setCausedBy(String causedBy) {
        this.causedBy = causedBy;
    }

    public ArrayList<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(ArrayList<String> filePaths) {
        this.filePaths = filePaths;
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
