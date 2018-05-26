package classes;

/**
 * Created by A.Mohammadi on 7/9/2017.
 */

public class Question {

    private long id;
    private String question;
    private String answer;
    private String answerMax150;

    public Question(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerMax150() {
        return answerMax150;
    }

    public void setAnswerMax150(String answerMax150) {
        this.answerMax150 = answerMax150;
    }

}
