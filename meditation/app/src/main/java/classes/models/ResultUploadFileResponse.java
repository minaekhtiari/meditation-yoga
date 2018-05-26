
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultUploadFileResponse {

    @SerializedName("FileID")
    @Expose
    private String fileID;
    @SerializedName("FileType")
    @Expose
    private int fileType;
    @SerializedName("FileName")
    @Expose
    private String fileName;
    @SerializedName("FileOriginalName")
    @Expose
    private String fileOriginalName;
    @SerializedName("Size")
    @Expose
    private int size;
    @SerializedName("SizeString")
    @Expose
    private String sizeString;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSizeString() {
        return sizeString;
    }

    public void setSizeString(String sizeString) {
        this.sizeString = sizeString;
    }

}
