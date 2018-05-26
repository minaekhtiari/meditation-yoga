
package com.hillavas.messaging.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("AnswerId")
    @Expose
    private int answerId;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("DoctorPictureFileId")
    @Expose
    private Object doctorPictureFileId;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("AnswerDateSh")
    @Expose
    private String answerDateSh;
    @SerializedName("AnswerDateMi")
    @Expose
    private String answerDateMi;
    @SerializedName("AttachedFiles")
    @Expose
    private List<AttachedFile> attachedFiles = null;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Object getDoctorPictureFileId() {
        return doctorPictureFileId;
    }

    public void setDoctorPictureFileId(Object doctorPictureFileId) {
        this.doctorPictureFileId = doctorPictureFileId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerDateSh() {
        return answerDateSh;
    }

    public void setAnswerDateSh(String answerDateSh) {
        this.answerDateSh = answerDateSh;
    }

    public String getAnswerDateMi() {
        return answerDateMi;
    }

    public void setAnswerDateMi(String answerDateMi) {
        this.answerDateMi = answerDateMi;
    }

    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
}
