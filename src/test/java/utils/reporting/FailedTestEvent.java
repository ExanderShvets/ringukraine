package utils.reporting;

public class FailedTestEvent {
    private String className;
    private String testName;
    private String groupName;
    private String testIdValue;
    private String browser;
    private String locale;
    private String pcName;
    private String pcOs;
    private String params;
    private String date;
    private String url;
    private String webSite;
    private String steps;
    private String causedBy;


    public void setClassName(String className) {
        this.className = className;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public void setTestIdValue(String testIdValue) {
        this.testIdValue = testIdValue;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public void setPcOs(String pcOs) {
        this.pcOs = pcOs;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public void setCausedBy(String causedBy) {
        this.causedBy = causedBy;
    }

    public void setPngImage(String pngImage) {
    }

    public void setGifImage(String gifImage) {
    }

    @Override
    public String toString() {
        return "FailedTestEvent{" +
                "className='" + className + '\'' +
                ", testName='" + testName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", testIdValue='" + testIdValue + '\'' +
                ", browser='" + browser + '\'' +
                ", locale='" + locale + '\'' +
                ", pcName='" + pcName + '\'' +
                ", pcOs='" + pcOs + '\'' +
                ", params='" + params + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", webSite='" + webSite + '\'' +
                ", steps='" + steps + '\'' +
                ", causedBy='" + causedBy + '\'' +
                '}';
    }
}
