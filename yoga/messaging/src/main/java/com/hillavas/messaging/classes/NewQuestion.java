
package com.hillavas.messaging.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewQuestion {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("categoryid")
    @Expose
    private int categoryid;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("fileIds")
    @Expose
    private List<AttachedFile> fileids = null;

    public NewQuestion(String token, int categoryid, String subject, String body , List<AttachedFile> fileids) {
        this.token = token;
        this.categoryid = categoryid;
        this.subject = subject;
        this.body = body;
        this.fileids = fileids;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<AttachedFile> getFileids() {
        return fileids;
    }

    public void setFileids(List<AttachedFile> fileids) {
        this.fileids = fileids;
    }
}
