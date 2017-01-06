package khangit96.quanlycaphe.model;

/**
 * Created by Administrator on 12/19/2016.
 */

public class Config {

    public static String COMPANY_KEY;
    public String companyAddress;
    public String companyName;
    public String companyServer;
    public String key;

    public Config(String companyName, String companyServer, String companyAddress) {
        this.companyName = companyName;
        this.companyServer = companyServer;
        this.companyAddress = companyAddress;
    }

    public Config() {
    }

}
