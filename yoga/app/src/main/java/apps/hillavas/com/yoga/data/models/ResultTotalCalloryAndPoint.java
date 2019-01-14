
package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultTotalCalloryAndPoint {

    @SerializedName("TotalPoint")
    @Expose
    private int totalPoint;
    @SerializedName("TotalCalory")
    @Expose
    private int totalCalory;

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getTotalCalory() {
        return totalCalory;
    }

    public void setTotalCalory(int totalCalory) {
        this.totalCalory = totalCalory;
    }

}
