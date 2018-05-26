package apps.hillavas.com.meditation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hillavas.messaging.helpers.QuestionHelper;

import java.util.List;

import factories.FragmentHelper;
import fragments.Fragment_Messages;
import fragments.Fragment_ToolbarAll_withoutMessage;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MessagingActivity extends AppCompatActivity {

    public static final String SENT_MESSAGE="SENT_MESSAGE";
    public static final String GUID="GUID";
    public static final String UNREAD_ANSWERS="UNREAD_ANSWERS";
    SharedPreferences sharedPreferencesHome;
    String token = null;
    FrameLayout frameLayoutMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferencesHome.getString(GUID, "");
        frameLayoutMessage = (FrameLayout) findViewById(R.id.fragment_messaging_frameBase);
        Fragment_ToolbarAll_withoutMessage toolbarAll_withoutMessage = new Fragment_ToolbarAll_withoutMessage();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE" , getString(R.string.messageBox));
        toolbarAll_withoutMessage.setArguments(bundle);
        new FragmentHelper(
                toolbarAll_withoutMessage,
                R.id.fraqment_messaging_toolbarFrame,
                getSupportFragmentManager()
        ).replace(false);

        new FragmentHelper(
                new Fragment_Messages(),
                R.id.fragment_messaging_frameBase,
                getSupportFragmentManager()
        ).replace(false);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new TaskGetUnreadAnswerCount().execute();
    }


    class TaskGetUnreadAnswerCount extends AsyncTask<Void , Void , List<Integer>> {

        @Override
        protected List<Integer> doInBackground(Void... params) {
            List<Integer> questionIdsWithNewAnswers = null;
            questionIdsWithNewAnswers = QuestionHelper.getUnreadAnswerQuestionIds(token);
            return questionIdsWithNewAnswers;
        }

        @Override
        protected void onPostExecute(List<Integer> integers) {
            super.onPostExecute(integers);
            if (integers == null)
                return;
                sharedPreferencesHome.edit().putInt(UNREAD_ANSWERS, integers.size()).commit();

        }
    }
}
