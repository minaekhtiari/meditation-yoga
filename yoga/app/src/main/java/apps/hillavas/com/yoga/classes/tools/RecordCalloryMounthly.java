
package apps.hillavas.com.yoga.classes.tools;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordCalloryMounthly {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Total")
    @Expose
    private int total;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
