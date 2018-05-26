
package com.hillavas.messaging.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordsForAllUsual {

    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("AnswersForAllUsual")
    @Expose
    private List<AnswersForAllUsual> answersForAllUsual = null;
    @SerializedName("HasAnswer")
    @Expose
    private boolean hasAnswer;
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
    private String insertDateSh;
    @SerializedName("InsertDateTimeMi")
    @Expose
    private String insertDateTimeMi;
    @SerializedName("IsReaded")
    @Expose
    private boolean isReaded;
    @SerializedName("Attachs")
    @Expose
    private Object attachs;
    @SerializedName("AttachedFiles")
    @Expose
    private List<Object> attachedFiles = null;
    @SerializedName("HasFileAttach")
    @Expose
    private boolean hasFileAttach;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<AnswersForAllUsual> getAnswersForAllUsual() {
        return answersForAllUsual;
    }

    public void setAnswersForAllUsual(List<AnswersForAllUsual> answersForAllUsual) {
        this.answersForAllUsual = answersForAllUsual;
    }

    public boolean isHasAnswer() {
        return hasAnswer;
    }

    public void setHasAnswer(boolean hasAnswer) {
        this.hasAnswer = hasAnswer;
    }

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

    public String getInsertDateSh() {
        return insertDateSh;
    }

    public void setInsertDateSh(String insertDateSh) {
        this.insertDateSh = insertDateSh;
    }

    public String getInsertDateTimeMi() {
        return insertDateTimeMi;
    }

    public void setInsertDateTimeMi(String insertDateTimeMi) {
        this.insertDateTimeMi = insertDateTimeMi;
    }

    public boolean isIsReaded() {
        return isReaded;
    }

    public void setIsReaded(boolean isReaded) {
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

}
