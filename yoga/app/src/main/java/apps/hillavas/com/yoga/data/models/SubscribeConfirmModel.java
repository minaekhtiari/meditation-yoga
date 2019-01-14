
package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscribeConfirmModel {

//    @SerializedName("MobileNumber")
//    @Expose
//    private long mobileNumber;

    @SerializedName("TransactionId")
    @Expose
    private String transactionId;

    @SerializedName("ServiceId")
    @Expose
    private String servicecId;

    @SerializedName("pin")
    @Expose
    private String pin;

    public SubscribeConfirmModel() {
        servicecId="11";
    }
    public String getServicecId() {
        return servicecId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
