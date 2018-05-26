
package com.hillavas.filemanaging.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultJsonGetFileByteArray {

    @SerializedName("IsSuccessfull")
    @Expose
    private boolean isSuccessfull;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Result")
    @Expose
    private FileByteArrayResult result;

    public boolean isIsSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public void setSuccessfull(boolean successfull) {
        isSuccessfull = successfull;
    }

    public FileByteArrayResult getResult() {
        return result;
    }

    public void setResult(FileByteArrayResult result) {
        this.result = result;
    }
}
