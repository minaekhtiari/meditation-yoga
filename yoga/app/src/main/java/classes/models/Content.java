
package classes.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

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
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("InsertDateSh")
    @Expose
    private String insertDateSh;
    @SerializedName("Attachs")
    @Expose
    private String attachs;
    @SerializedName("AttachedFiles")
    @Expose
    private List<AttachedFile> attachedFiles = null;
    @SerializedName("HasFileAttach")
    @Expose
    private boolean hasFileAttach;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getInsertDateSh() {
        return insertDateSh;
    }

    public void setInsertDateSh(String insertDateSh) {
        this.insertDateSh = insertDateSh;
    }

    public String getAttachs() {
        return attachs;
    }

    public void setAttachs(String attachs) {
        this.attachs = attachs;
    }

    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public boolean isHasFileAttach() {
        return hasFileAttach;
    }

    public void setHasFileAttach(boolean hasFileAttach) {
        this.hasFileAttach = hasFileAttach;
    }

}
