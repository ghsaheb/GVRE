package RealEstate;

public class RealEstate extends User {
    private String URL;

    public RealEstate(String name, String URL) {
        super(name);
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
