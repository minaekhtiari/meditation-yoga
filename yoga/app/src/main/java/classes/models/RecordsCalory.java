
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordsCalory {

    @SerializedName("CaloryHistoryId")
    @Expose
    private int caloryHistoryId;
    @SerializedName("Point")
    @Expose
    private int point;
    @SerializedName("TotalCalory")
    @Expose
    private int totalCalory;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("InsertDateSh")
    @Expose
    private String insertDateSh;
    @SerializedName("InsertDateMi")
    @Expose
    private String insertDateMi;
    @SerializedName("Body")
    @Expose
    private String body;

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

    public String getInsertDateMi() {
        return insertDateMi;
    }

    public void setInsertDateMi(String insertDateMi) {
        this.insertDateMi = insertDateMi;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
