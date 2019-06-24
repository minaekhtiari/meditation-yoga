
package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscribeModel {


    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("Channel")
    @Expose
    private String Channel;

    @SerializedName("ServiceId")
    @Expose
    private String servicecId;


    @SerializedName("AppName")
    @Expose
    private String AppName;

    @SerializedName("AppVersion")
    @Expose
    private String AppVersion;

    @SerializedName("DeviceManufacture")
    @Expose
    private String DeviceManufacture;

    @SerializedName("DeviceModel")
    @Expose
    private String DeviceModel;

    @SerializedName("OsVersion")
    @Expose
    private String OsVersion;


    @SerializedName("Os")
    @Expose
    private String Os;

    public SubscribeModel() {

        Channel = "A-HillaVas";
        servicecId="11";
    }

    public String getMobileNumber() {

        return MobileNumber;
    }

    public String getServicecId() {
        return servicecId;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }


    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setDeviceManufacture(String deviceManufacture) {
        DeviceManufacture = deviceManufacture;
    }

    public String getDeviceManufacture() {
        return DeviceManufacture;
    }

    public void setDeviceModel(String deviceModel) {
        DeviceModel = deviceModel;
    }

    public String getDeviceModel() {
        return DeviceModel;
    }


    public void setOs(String os) {
        Os = os;
    }

    public String getOs() {
        return Os;
    }

    public void setOsVersion(String osVersion) {
        OsVersion = osVersion;
    }

    public String getOsVersion() {
        return OsVersion;
    }
}
