package by.company.app.driver;

public enum Browser {

    CHROME_80("chrome", "80.0");

    private String name;
    private String version;

    Browser(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

}
