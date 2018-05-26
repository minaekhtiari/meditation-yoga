
package com.hillavas.messaging.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswersForAllUsual {

    @SerializedName("AnswerId")
    @Expose
    private int answerId;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("AnswerDateSh")
    @Expose
    private String answerDateSh;
    @SerializedName("AnswerDateMi")
    @Expose
    private String answerDateMi;
    @SerializedName("DoctorName")
    @Expose
    private Object doctorName;
    @SerializedName("DoctorPictureFileId")
    @Expose
    private Object doctorPictureFileId;
    @SerializedName("Attachs")
    @Expose
    private Object attachs;
    @SerializedName("AttachedFiles")
    @Expose
    private List<Object> attachedFiles = null;
    @SerializedName("HasFileAttach")
    @Expose
    private boolean hasFileAttach;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

    public Object getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(Object doctorName) {
        this.doctorName = doctorName;
    }

    public Object getDoctorPictureFileId() {
        return doctorPictureFileId;
    }

    public void setDoctorPictureFileId(Object doctorPictureFileId) {
        this.doctorPictureFileId = doctorPictureFileId;
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
