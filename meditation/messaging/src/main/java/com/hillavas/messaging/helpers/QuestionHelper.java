package com.hillavas.messaging.helpers;

import com.hillavas.messaging.classes.Answer;
import com.hillavas.messaging.classes.JsonResultBoolean;
import com.hillavas.messaging.classes.JsonResultUnreadCount;
import com.hillavas.messaging.classes.NewQuestion;
import com.hillavas.messaging.classes.Question;
import com.hillavas.messaging.classes.Record;
import com.hillavas.messaging.classes.ResultForAllUsual;
import com.hillavas.messaging.classes.ResultJson;
import com.hillavas.messaging.classes.ResultJsonAllAnswer;
import com.hillavas.messaging.classes.ResultJsonBase;
import com.hillavas.messaging.classes.ResultJsonForAllUsual;
import com.hillavas.messaging.classes.ResultJsonOneQuestion;
import com.hillavas.messaging.factories.RetrofitMessagingFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A.Mohammadi on 8/9/2017.
 */

public class QuestionHelper {

    private static IRetrofitMessaging retrofitMessagingClient = RetrofitMessagingFactory.getRetrofitClient();
    private static boolean sent = false;



    public static Boolean sendQuestion(String token , Question question , int categoryId){
        NewQuestion newQuestion = new NewQuestion(token , categoryId , question.getSubject() , question.getBody() , question.getAttachedFiles());
        Boolean sent = false;
        try {
            ResultJsonBase rj = retrofitMessagingClient.sendNewQuestion(newQuestion).execute().body();
            if(rj.isIsSuccessfull())
                sent = true;
            else
                sent = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sent;
    }



//    public static Boolean sendQuestion(String token , Question question , int categoryId){
//        NewQuestion newQuestion = new NewQuestion(token , categoryId , question.getSubject() , question.getBody());
//
//        retrofitMessagingClient.sendNewQuestion(newQuestion).enqueue(new Callback<ResultJson>() {
//            @Override
//            public void onResponse(Call<ResultJson> call, Response<ResultJson> response) {
//                if(response != null)
//                    if(response.body().isIsSuccessfull())
//                        sent = true;
//            }
//            @Override
//            public void onFailure(Call<ResultJson> call, Throwable t) {
//
//            }
//        });
//        return sent;
//    }


    public static List<Question> getAllQuestions(String token){
        List<Question> questions = new ArrayList<>();
        List<Record> records = new ArrayList<>();
        try {
            ResultJson rj = retrofitMessagingClient.getAllQuestion(token).execute().body();
            if(rj != null && rj.isIsSuccessfull())
                records = rj.getResult().getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(records != null){
            for(Record record : records){
                Question question = new Question();
                question.setQuestionId(record.getQuestionId());
                question.setMemberUserName(record.getMemberUserName());
                question.setBody(record.getBody());
                question.setIsReaded(record.getIsReaded());
                question.setInsertDateSh(record.getInsertDateSh());
                question.setSubject(record.getSubject());
                question.setCategoryTitle(record.getCategoryTitle());
                question.setMemberPictureId(record.getMemberPictureId());
                question.setInsertDateTimeMi(record.getInsertDateTimeMi());
                question.setHasFileAttach(record.isHasFileAttach());
                question.setAttachs(record.getAttachs());
                questions.add(question);
            }
        }
        return questions;
    }

    public static List<Question> getAllUsualQuestions(String token){
        List<Question> questions = new ArrayList<>();
        List<Record> records = new ArrayList<>();
        try {
            ResultJson js = retrofitMessagingClient.getAllUsual(token , 1 , 100).execute().body();
            if(js != null && js.isIsSuccessfull())
                records = js.getResult().getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(records != null){
            for(Record record : records){
                Question question = new Question();
                question.setQuestionId(record.getQuestionId());
                question.setMemberUserName(record.getMemberUserName());
                question.setBody(record.getBody());
                question.setIsReaded(record.getIsReaded());
                question.setInsertDateSh(record.getInsertDateSh());
                question.setSubject(record.getSubject());
                question.setCategoryTitle(record.getCategoryTitle());
                question.setMemberPictureId(record.getMemberPictureId());
                question.setInsertDateTimeMi(record.getInsertDateTimeMi());
                question.setAnswers(record.getAnswers());
                questions.add(question);
                question.setAnswerBody(record.getAnswerBody());
            }
        }
        return questions;
    }

    public static Question getQuestion(String token , int questionId){
        Question question;
        ResultJsonOneQuestion resultJsonOneQuestion = null;
        try {
            resultJsonOneQuestion = retrofitMessagingClient.getQuestion(token , questionId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(resultJsonOneQuestion.isIsSuccessfull()){
            return resultJsonOneQuestion.getResult();
        }
        return null;
    }

    public static List<Answer> getAllAnswerByQuestionId(String token , int questionId){

        ResultJsonAllAnswer resultJsonAllAnswer = null;
        try {
            resultJsonAllAnswer = retrofitMessagingClient.getAnswersByQuestionId(token , questionId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(resultJsonAllAnswer.isIsSuccessfull()){
            return resultJsonAllAnswer.getAnswers();
        }
        return null;
    }

    public static boolean readedAnswer(String token , int questionId){
        boolean readed = false;
        try {
            JsonResultBoolean jrb =retrofitMessagingClient.readedAnswerQuestion(token , questionId).execute().body();
            readed = jrb.isResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readed;
    }

    public static List<Integer> getUnreadAnswerQuestionIds(String token){
        List<Integer> unreadQuestionIds = new ArrayList<>();
        JsonResultUnreadCount jsonResultUnreadCount= null;
        try {
            jsonResultUnreadCount = retrofitMessagingClient.getUnreadAnswerCount(token).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(jsonResultUnreadCount != null){
            if(jsonResultUnreadCount.isIsSuccessfull()){
                unreadQuestionIds = jsonResultUnreadCount.getUnreads().getQuestionIds();
            }
        }
        return unreadQuestionIds;
    }
}
