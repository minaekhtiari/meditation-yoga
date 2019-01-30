package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultJsonIrancellMemberSignUp {


    @SerializedName("IsSuccessfull")
    @Expose
    private Boolean isSuccessfull;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private String result;

    public Boolean getIsSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(Boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
