
package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaloryAdd {

    public CaloryAdd(String token , int contentId){
        this.Token = token;
        this.ContentId = contentId;
    }

    @SerializedName("Token")
    @Expose
    private String Token;
    @SerializedName("ContentId")
    @Expose
    private int ContentId;


    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getContentId() {
        return ContentId;
    }

    public void setContentId(int contentId) {
        ContentId = contentId;
    }
}
