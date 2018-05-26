
package com.hillavas.filemanaging.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hillavas.filemanaging.helpers.FileManagerHelper;

public class FileByteArrayResult {

    @SerializedName("File")
    @Expose
    private List<Integer> file = null;
    @SerializedName("FileType")
    @Expose
    private int fileType;
    @SerializedName("FileName")
    @Expose
    private String fileName;

    public List<Integer> getFile() {
        return file;
    }

    public void setFile(List<Integer> file) {
        this.file = file;
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

    public byte[] getByteArrays(List<Integer> lstInteger){
        byte[] byteArray = null;
        byteArray = new byte[lstInteger.size()];
        for(int i = 0 ; i<lstInteger.size() ; i++) {
                byteArray[i] = lstInteger.get(i).byteValue();
        }
        return byteArray;
    }

}
