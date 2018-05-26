
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultCalloryHistoryDaily {

    @SerializedName("CaloryHistoryId")
    @Expose
    private int caloryHistoryId;
    @SerializedName("Point")
    @Expose
    private int point;
    @SerializedName("ContentLength")
    @Expose
    private int contentLength;
    @SerializedName("TotalCalory")
    @Expose
    private int totalCalory;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("InsertDateSh")
    @Expose
    private String insertDateSh;
    @SerializedName("Time")
    @Expose
    private String time;

    public int getCaloryHistoryId() {
        return caloryHistoryId;
    }

    public void setCaloryHistoryId(int caloryHistoryId) {
        this.caloryHistoryId = caloryHistoryId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public int getTotalCalory() {
        return totalCalory;
    }

    public void setTotalCalory(int totalCalory) {
        this.totalCalory = totalCalory;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getInsertDateSh() {
        return insertDateSh;
    }

    public void setInsertDateSh(String insertDateSh) {
        this.insertDateSh = insertDateSh;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
