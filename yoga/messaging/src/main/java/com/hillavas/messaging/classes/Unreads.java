
package com.hillavas.messaging.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unreads {

    @SerializedName("Count")
    @Expose
    private int count;
    @SerializedName("QuestionIds")
    @Expose
    private List<Integer> questionIds = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }

}
