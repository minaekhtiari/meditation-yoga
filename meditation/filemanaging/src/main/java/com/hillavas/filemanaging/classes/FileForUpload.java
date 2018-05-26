
package com.hillavas.filemanaging.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileForUpload {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("StringBase64")
    @Expose
    private String stringBase64;
    @SerializedName("tags")
    @Expose
    private String tags;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStringBase64() {
        return stringBase64;
    }

    public void setStringBase64(String stringBase64) {
        this.stringBase64 = stringBase64;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
