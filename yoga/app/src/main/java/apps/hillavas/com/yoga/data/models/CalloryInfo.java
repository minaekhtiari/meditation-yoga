
package apps.hillavas.com.yoga.data.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalloryInfo {

    @SerializedName("TotalRecord")
    @Expose
    private int totalRecord;
    @SerializedName("PageNumber")
    @Expose
    private int pageNumber;
    @SerializedName("RowCount")
    @Expose
    private int rowCount;
    @SerializedName("CalloryRecord")
    @Expose
    private List<CalloryRecord> calloryRecord = null;

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<CalloryRecord> getCalloryRecord() {
        return calloryRecord;
    }

    public void setCalloryRecord(List<CalloryRecord> calloryRecord) {
        this.calloryRecord = calloryRecord;
    }

}
