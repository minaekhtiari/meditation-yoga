
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class ProfileInfo {

    @SerializedName("MobileNumber")
    @Expose
    private BigInteger mobileNumber;
    @SerializedName("Credit")
    @Expose
    private int credit;
    @SerializedName("IsServiceOn")
    @Expose
    private boolean isServiceOn;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Sex")
    @Expose
    private boolean sex;
    @SerializedName("IsBlock")
    @Expose
    private boolean isBlock;
    @SerializedName("ImageFileId")
    @Expose
    private Object imageFileId;
    @SerializedName("LevelTitle")
    @Expose
    private String levelTitle;


    public BigInteger getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(BigInteger mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isIsServiceOn() {
        return isServiceOn;
    }

    public void setIsServiceOn(boolean isServiceOn) {
        this.isServiceOn = isServiceOn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean isIsBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public Object getImageFileId() {
        return imageFileId;
    }

    public void setImageFileId(Object imageFileId) {
        this.imageFileId = imageFileId;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

}
