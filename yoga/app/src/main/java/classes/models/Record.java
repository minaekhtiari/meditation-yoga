
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("ContentId")
    @Expose
    private int contentId;
    @SerializedName("LikeCount")
    @Expose
    private int likeCount;
    @SerializedName("ViewCount")
    @Expose
    private int viewCount;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Price")
    @Expose
    private int price;
    @SerializedName("IsFree")
    @Expose
    private boolean isFree;
    @SerializedName("ThumbnailImageId")
    @Expose
    private String thumbnailImageId;
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("InsertDateSh")
    @Expose
    private String insertDateSh;
    @SerializedName("Point")
    @Expose
    private int point;
    @SerializedName("Calory")
    @Expose
    private float calory;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isIsFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    public String getThumbnailImageId() {
        return thumbnailImageId;
    }

    public void setThumbnailImageId(String thumbnailImageId) {
        this.thumbnailImageId = thumbnailImageId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getInsertDateSh() {
        return insertDateSh;
    }

    public void setInsertDateSh(String insertDateSh) {
        this.insertDateSh = insertDateSh;
    }

    public int getPoint() {

        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public float getCalory() {
        return calory;
    }

    public void setCalory(float calory) {
        this.calory = calory;
    }
}
