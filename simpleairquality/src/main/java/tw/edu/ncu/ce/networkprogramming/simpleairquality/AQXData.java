package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mpclab on 2015/5/27.
 */
public class AQXData {

    private String SiteName;
    private String Status;
    private String SO2;


    private String CO;
    private String O3;
    private String PM10;
    private String NO2;
    private String FPMI;
    private String PublishTime;


    private String PSI;

    @SerializedName("PM2.5")
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

    public String getPSI() {
        return PSI;
    }

    public void setPSI(String PSI) {
        this.PSI = PSI;
    }

    public String getSO2() {
        return SO2;
    }

    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }

    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
    }

    public String getO3() {
        return O3;
    }

    public void setO3(String o3) {
        O3 = o3;
    }

    public String getPM10() {
        return PM10;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public String getNO2() {
        return NO2;
    }

    public void setNO2(String NO2) {
        this.NO2 = NO2;
    }

    public String getFPMI() {
        return FPMI;
    }

    public void setFPMI(String FPMI) {
        this.FPMI = FPMI;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
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
