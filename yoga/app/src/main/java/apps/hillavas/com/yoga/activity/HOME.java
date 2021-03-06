package apps.hillavas.com.yoga.activity;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.RetrofitIrancell.CharkhonehHttpFactory;
import apps.hillavas.com.yoga.activity.sign.FragmentSubscribe;
import apps.hillavas.com.yoga.classes.Home_Menu_Page;
import apps.hillavas.com.yoga.data.models.IsSubMtn;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HOME extends AppCompatActivity {

    private static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String COMPELETE_REGISTER="COMPELETE_REGISTER";
    public static final String MOBILE_NUMBER="MOBILE_NUMBER";
    public static final String PASSWORD="PASSWORD";
    public static final String REGISTERED = "REGISTERED";
    public static final String GUID="GUID";
    public static final String UNREAD_ANSWERS="UNREAD_ANSWERS";
    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String STOP_LOADING = "STOP_LOADING";
    public static final String IS_IRANCELL="IS_IRANCELL";
    public static final String IS_HAMRAHAVAL="IS_HAMRAHAVAL";
    SharedPreferences sharedPreferencesHome;
    FragmentTransaction transaction;
    String token = null;
    FrameLayout frameBase;
    FrameLayout frameFinish;
    String purchasToken;

    boolean compeleteRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferencesHome.edit().putInt(FONT_SIZE , sharedPreferencesHome.getInt(FONT_SIZE , 12)).commit();
        sharedPreferencesHome.edit().putInt(CATEGORY_ID , 10).commit();

         purchasToken = sharedPreferencesHome.getString("PurchaseToken", "");
        token = sharedPreferencesHome.getString(GUID, "");
        compeleteRegister = sharedPreferencesHome.getBoolean(COMPELETE_REGISTER, false);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));


        frameBase = (FrameLayout) findViewById(R.id.frameLayout_base);
        frameFinish = (FrameLayout) findViewById(R.id.frameLayout_finish);
        transaction = getSupportFragmentManager().beginTransaction();
        if(token != null && token.length() > 0 ) {

            if(compeleteRegister){
                if(sharedPreferencesHome.getBoolean(IS_IRANCELL,true)){
                    checkIsSub();

                }else{
                Intent intent = new Intent(this , FirstContentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                }
            }else {
                if(sharedPreferencesHome.getBoolean(IS_IRANCELL,true)){
                    checkIsSub();

                }else{
                Intent intent = new Intent(this, ProfileInfo_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);}
            }
        }else {

            Intent intent = new Intent(this, ChooseOperator.class);
            startActivity(intent);


            sharedPreferencesHome.edit().putLong(MOBILE_NUMBER, 0).commit();
            sharedPreferencesHome.edit().putString(PASSWORD, "").commit();

        }
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }

    }

//    class TaskGetUnreadAnswerCount extends AsyncTask<Void , Void , List<Integer>>{
//        @Override
//        protected List<Integer> doInBackground(Void... params) {
//            List<Integer> questionIdsWithNewAnswers = null;
//            questionIdsWithNewAnswers = QuestionHelper.getUnreadAnswerQuestionIds(token);
//            return questionIdsWithNewAnswers;
//        }
//        @Override
//        protected void onPostExecute(List<Integer> integers) {
//            super.onPostExecute(integers);
//            if(integers == null)
//                return;
//            sharedPreferencesHome.edit().putInt(UNREAD_ANSWERS , integers.size()).commit();
//        }
//    }


    private void checkIsSub() {
        CharkhonehHttpFactory.getRetrofitClient().IsSubscribe("apps.hillavas.com.yoga",
                "Meditation_SKU", purchasToken, "9d568991-f5cf-386f-a740-9e66fd992588")
                .enqueue(new Callback<IsSubMtn>() {
            @Override
            public void onResponse(Call<IsSubMtn> call, Response<IsSubMtn> response) {
                if (response.body().getAutoRenewing()) {
                    Long expiryTimeMillis = response.body().getExpiryTimeMillis()/1000L;
                    Long currentTime = System.currentTimeMillis() / 1000L;

                    if (expiryTimeMillis >= currentTime) {
                        if(compeleteRegister) {
                            Intent intent = new Intent(HOME.this, FirstContentActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(HOME.this, ProfileInfo_Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                }

                else {
                    Intent intent = new Intent(HOME.this, ChooseOperator.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<IsSubMtn> call, Throwable t) {
                Intent intent = new Intent(HOME.this, FirstContentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

}
