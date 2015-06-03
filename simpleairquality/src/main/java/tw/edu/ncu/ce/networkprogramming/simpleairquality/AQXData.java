package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


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

    public List<String> getDetails(){
        List<String> details = new ArrayList<>();

        details.add("空氣污染指標(PSI):"+PSI);
        details.add("二氧化硫濃度:"+SO2);
        details.add("一氧化碳濃度:"+CO);
        details.add("臭氧濃度:"+O3);
        details.add("懸浮微粒濃度(PM10):"+PM10);
        details.add("細懸浮微粒濃度(PM2.5):"+PM2_5);
        details.add("二氧化氮濃度:"+NO2);
        details.add("發布時間:"+PublishTime);

        return details;
    }
}
