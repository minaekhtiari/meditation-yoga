package apps.hillavas.com.yoga.data.models;

/**
 * Created by A.Mohammadi on 11/25/2017.
 */

public class OtpConfirmation {
    private Long mobileNumber;
    private int requestId;
    private int pin;

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
