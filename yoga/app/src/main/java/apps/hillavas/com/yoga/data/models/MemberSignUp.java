
package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberSignUp {


    @SerializedName("Token")
    @Expose
    private String token;


    public MemberSignUp() {

    }

    public String getToken() {
        return token;
    }
}
