
package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachedFile {

    @SerializedName("AttachedFileId")
    @Expose
    private int attachedFileId;
    @SerializedName("FileID")
    @Expose
    private String fileID;
    @SerializedName("FileType")
    @Expose
    private int fileType;
    @SerializedName("ContentType")
    @Expose
    private String contentType;

    public int getAttachedFileId() {
        return attachedFileId;
    }

    public void setAttachedFileId(int attachedFileId) {
        this.attachedFileId = attachedFileId;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
