package utils.reporting;

public class FailedTestEvent {
    private String className;
    private String testName;
    private String groupName;
    private String testIdValue;
    private String browser;
    private String locale;
    private String sysweb;
    private String pcName;
    private String pcOs;
    private String params;
    private String date;
    private String url;
    private String webSite;
    private String steps;
    private String causedBy;
    private String pngImage;
    private String gifImage;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTestIdValue() {
        return testIdValue;
    }

    public void setTestIdValue(String testIdValue) {
        this.testIdValue = testIdValue;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSysweb() {
        return sysweb;
    }

    public void setSysweb(String sysweb) {
        this.sysweb = sysweb;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getPcOs() {
        return pcOs;
    }

    public void setPcOs(String pcOs) {
        this.pcOs = pcOs;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getCausedBy() {
        return causedBy;
    }

    public void setCausedBy(String causedBy) {
        this.causedBy = causedBy;
    }

    public String getPngImage() {
        return pngImage;
    }

    public void setPngImage(String pngImage) {
        this.pngImage = pngImage;
    }

    public String getGifImage() {
        return gifImage;
    }

    public void setGifImage(String gifImage) {
        this.gifImage = gifImage;
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
                ", sysweb='" + sysweb + '\'' +
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
