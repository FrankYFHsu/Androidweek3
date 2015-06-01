package tw.edu.ncu.ce.networkprogramming.xmlexample;

/**
 * Created by mpclab on 2015/5/27.
 */
public class AQXData {


    private String SiteName;
    private String Status;

    private String PM2_5;

    public AQXData(String SiteName, String Status, String PM25) {
        this.SiteName = SiteName;
        this.Status = Status;
        this.PM2_5 = PM25;
    }




    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public String getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(String PM2_5) {
        this.PM2_5 = PM2_5;
    }


    public void setSiteName(String siteName) {
        this.SiteName = siteName;
    }

    public String getSiteName() {
        return SiteName;
    }


    @Override
    public String toString() {

        return "地點" + ":"
                + SiteName + ","
                + "空氣品質" + ":"
                + Status + ","
                + "PM2.5" + ":"
                + PM2_5;


    }
}
