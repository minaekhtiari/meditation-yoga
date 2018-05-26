
package com.hillavas.messaging.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachedFile {

    @SerializedName("FileID")
    @Expose
    private String fileID;
    @SerializedName("FileType")
    @Expose
    private int fileType;


    public AttachedFile(String fileID, int fileType) {

        this.fileID = fileID;
        this.fileType = fileType;

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

}
