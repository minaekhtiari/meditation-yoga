
package com.hillavas.filemanaging.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileURLResult {

    @SerializedName("FileAddress")
    @Expose
    private String fileAddress;
    @SerializedName("FileType")
    @Expose
    private int fileType;
    @SerializedName("FileName")
    @Expose
    private String fileName;

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
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

}
