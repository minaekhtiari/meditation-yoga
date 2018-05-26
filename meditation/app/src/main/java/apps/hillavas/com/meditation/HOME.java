package apps.hillavas.com.meditation;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import fragments.Fragment_Register;
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
    SharedPreferences sharedPreferencesHome;
    FragmentTransaction transaction;
    String token = null;
    FrameLayout frameBase;
    FrameLayout frameFinish;
    boolean compeleteRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferencesHome.edit().putInt(FONT_SIZE , sharedPreferencesHome.getInt(FONT_SIZE , 12)).commit();
        sharedPreferencesHome.edit().putInt(CATEGORY_ID , 10).commit();
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
                Intent intent = new Intent(this , FirstContentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, ProfileInfo_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }else {
            transaction.replace(R.id.frameLayout_base, new Fragment_Register()).commit();
            sharedPreferencesHome.edit().putLong(MOBILE_NUMBER, 0).commit();
            sharedPreferencesHome.edit().putString(PASSWORD, "").commit();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @Override
    protected void onPause() {
        super.onPause();

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


}
