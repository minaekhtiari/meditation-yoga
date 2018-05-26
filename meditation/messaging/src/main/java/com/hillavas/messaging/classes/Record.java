
package com.hillavas.messaging.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("QuestionId")
    @Expose
    private int questionId;
    @SerializedName("CategoryTitle")
    @Expose
    private String categoryTitle;
    @SerializedName("MemberUserName")
    @Expose
    private String memberUserName;
    @SerializedName("MemberPictureId")
    @Expose
    private Object memberPictureId;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("InsertDateSh")
    @Expose
    private Object insertDateSh;
    @SerializedName("InsertDateTimeMi")
    @Expose
    private String insertDateTimeMi;
    @SerializedName("IsReaded")
    @Expose
    private Object isReaded;
    @SerializedName("Attachs")
    @Expose
    private Object attachs;
    @SerializedName("AttachedFiles")
    @Expose
    private List<Object> attachedFiles = null;
    @SerializedName("HasFileAttach")
    @Expose
    private boolean hasFileAttach;
    @SerializedName("Answers")
    @Expose
    private List<Answer> answers;

    public String getAnswerBody() {
        return AnswerBody;
    }

    public void setAnswerBody(String answerBody) {
        AnswerBody = answerBody;
    }

    @SerializedName("AnswerBody")
    @Expose
    private String AnswerBody;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    public Object getMemberPictureId() {
        return memberPictureId;
    }

    public void setMemberPictureId(Object memberPictureId) {
        this.memberPictureId = memberPictureId;
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

    public Object getInsertDateSh() {
        return insertDateSh;
    }

    public void setInsertDateSh(Object insertDateSh) {
        this.insertDateSh = insertDateSh;
    }

    public String getInsertDateTimeMi() {
        return insertDateTimeMi;
    }

    public void setInsertDateTimeMi(String insertDateTimeMi) {
        this.insertDateTimeMi = insertDateTimeMi;
    }

    public Object getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(Object isReaded) {
        this.isReaded = isReaded;
    }

    public Object getAttachs() {
        return attachs;
    }

    public void setAttachs(Object attachs) {
        this.attachs = attachs;
    }

    public List<Object> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<Object> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public boolean isHasFileAttach() {
        return hasFileAttach;
    }

    public void setHasFileAttach(boolean hasFileAttach) {
        this.hasFileAttach = hasFileAttach;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
