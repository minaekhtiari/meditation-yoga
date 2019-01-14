
package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hillavas.messaging.classes.Answer;
import com.hillavas.messaging.classes.AttachedFile;

import java.util.List;

public class QuestionAndAnswerUsual {

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
    private List<com.hillavas.messaging.classes.AttachedFile> attachedFiles = null;
    @SerializedName("HasFileAttach")
    @Expose
    private boolean hasFileAttach;

    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public QuestionAndAnswerUsual(){}

    public QuestionAndAnswerUsual(String subject, String body) {
        this.subject = subject;
        this.body = body;
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

    public List<com.hillavas.messaging.classes.AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public boolean isHasFileAttach() {
        return hasFileAttach;
    }

    public void setHasFileAttach(boolean hasFileAttach) {
        this.hasFileAttach = hasFileAttach;
    }

}
