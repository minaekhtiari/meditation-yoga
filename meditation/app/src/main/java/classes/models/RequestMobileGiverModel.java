
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestMobileGiverModel {

    @SerializedName("MobileNumber")
    @Expose
    private long mobileNumber;

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
