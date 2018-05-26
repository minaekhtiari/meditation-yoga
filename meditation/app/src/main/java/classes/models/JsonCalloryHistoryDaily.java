
package classes.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonCalloryHistoryDaily {

    @SerializedName("IsSuccessfull")
    @Expose
    private boolean isSuccessfull;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Result")
    @Expose
    private List<ResultCalloryHistoryDaily> resultCalloryHistoryDaily = null;

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

    public List<ResultCalloryHistoryDaily> getResultCalloryHistoryDaily() {
        return resultCalloryHistoryDaily;
    }

    public void setResultCalloryHistoryDaily(List<ResultCalloryHistoryDaily> resultCalloryHistoryDaily) {
        this.resultCalloryHistoryDaily = resultCalloryHistoryDaily;
    }

}
