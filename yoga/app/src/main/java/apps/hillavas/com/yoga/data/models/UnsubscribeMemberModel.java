
package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnsubscribeMemberModel {


    public UnsubscribeMemberModel(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
