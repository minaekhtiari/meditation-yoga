
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileGiver {

    @SerializedName("IsSuccessfull")
    @Expose
    private boolean isSuccessfull;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private FileResult fileResult;

    public boolean isIsSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public void setSuccessfull(boolean successfull) {
        isSuccessfull = successfull;
    }

    public FileResult getFileResult() {
        return fileResult;
    }

    public void setFileResult(FileResult fileResult) {
        this.fileResult = fileResult;
    }
}
