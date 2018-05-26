
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonTotalCalloryAndPoints {

    @SerializedName("IsSuccessfull")
    @Expose
    private boolean isSuccessfull;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Result")
    @Expose
    private ResultTotalCalloryAndPoint resultTotalCalloryAndPoint;

    public boolean isIsSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public ResultTotalCalloryAndPoint getResultTotalCalloryAndPoint() {
        return resultTotalCalloryAndPoint;
    }

    public void setResultTotalCalloryAndPoint(ResultTotalCalloryAndPoint resultTotalCalloryAndPoint) {
        this.resultTotalCalloryAndPoint = resultTotalCalloryAndPoint;
    }

}
