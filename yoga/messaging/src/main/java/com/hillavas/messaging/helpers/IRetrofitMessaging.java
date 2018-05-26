package com.hillavas.messaging.helpers;

import com.hillavas.messaging.classes.JsonResultBoolean;
import com.hillavas.messaging.classes.JsonResultUnreadCount;
import com.hillavas.messaging.classes.NewQuestion;
import com.hillavas.messaging.classes.ResultForAllUsual;
import com.hillavas.messaging.classes.ResultJson;
import com.hillavas.messaging.classes.ResultJsonAllAnswer;
import com.hillavas.messaging.classes.ResultJsonBase;
import com.hillavas.messaging.classes.ResultJsonForAllUsual;
import com.hillavas.messaging.classes.ResultJsonOneQuestion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by A.Mohammadi on 8/9/2017.
 */

public interface IRetrofitMessaging {

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("Question/CreateQuestion")
    Call<ResultJsonBase> sendNewQuestion(@Body NewQuestion newQuestion);

    @GET("Question/getAll")
    Call<ResultJson> getAllQuestion(@Query("token") String token);

    @GET("UsualQuestion/getAllUsual")
    Call<ResultJson> getAllUsual(@Query("token") String token ,
                                            @Query("pageNumber") int pageNumber ,
                                            @Query("rowCount") int rowCount);

    @GET("Question/getQuestion")
    Call<ResultJsonOneQuestion> getQuestion(@Query("token") String token ,
                                            @Query("questionId") int questionId);

    @GET("answer/GetAnsweredByQuestionId")
    Call<ResultJsonAllAnswer> getAnswersByQuestionId(@Query("token") String token ,
                                                     @Query("questionId") int questionId);

    @GET("Question/ReadedAnswerWithMember")
    Call<JsonResultBoolean> readedAnswerQuestion(@Query("token") String token ,
                                                 @Query("questionId") int questionId);

    @GET("Answer/GetUnreadAnswered")
    Call<JsonResultUnreadCount> getUnreadAnswerCount(@Query("token") String token);

}
