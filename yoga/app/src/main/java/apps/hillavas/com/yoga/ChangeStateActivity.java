package apps.hillavas.com.yoga;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangeStateActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNewAnswerCount;
    ImageView ivNewAnswerCountBack;
    RelativeLayout relativeLayoutImageMessage;
    RelativeLayout relativeLayoutImageMenu;
    TextView tvAnswerCountNav;
    ImageView ivAnswerCountNavBack;
    ImageView ivMenu;
    public static final String SENT_MESSAGE="SENT_MESSAGE";
    public static final String GUID="GUID";
    public static final String UNREAD_ANSWERS="UNREAD_ANSWERS";
    SharedPreferences sharedPreferencesHome;
    String token = null;
    String title = "";
    int id = 0;
    int newAnswerCount = 0;
    Vibrator vibrator;

    EditText editMobileNumber;
    EditText editRequestCode;
    Button btnRegister;
    Button btnAccept;
    FrameLayout frame1;
    FrameLayout frame2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_state);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferencesHome.getString(GUID, "");
        title = getIntent().getStringExtra("MENU_ITEM");
        id = getIntent().getIntExtra("MENU_ID",0);
        ivMenu = (ImageView) findViewById(R.id.toolbar_all_imageBack);
//        ivMenu.setImageResource(R.drawable.back_icon);
        ((TextView)findViewById(R.id.toolbar_all_frameTitle_text)).setText("عضویت در سرویس");
//        tvNewAnswerCount = (TextView) findViewById(R.id.toolbar_all_text_newMessageCounter);
//        ivNewAnswerCountBack = (ImageView) findViewById(R.id.toolbar_all_image_newMessageCounterBack);
        relativeLayoutImageMessage = (RelativeLayout)findViewById(R.id.toolbar_all_imageMessage_relative);
        relativeLayoutImageMenu = (RelativeLayout)findViewById(R.id.toolbar_all_imageBack_relative);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        relativeLayoutImageMessage.setOnClickListener(this);
        relativeLayoutImageMenu.setOnClickListener(this);
        relativeLayoutImageMessage = (RelativeLayout) findViewById(R.id.toolbar_home_imageMessage_relative);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/iransans.ttf");
        ((TextView)findViewById(R.id.toolbar_all_frameTitle_text)).setTypeface(typeface);
        editMobileNumber = (EditText) findViewById(R.id.activity_change_edit_mobileNumber);
        editRequestCode = (EditText) findViewById(R.id.activity_change_edit_code);
        btnRegister = (Button) findViewById(R.id.activity_change_btn_register);
        btnAccept = (Button) findViewById(R.id.activity_change_btn_accept);
        frame1 = (FrameLayout) findViewById(R.id.activity_change_frame_1);
        frame2 = (FrameLayout) findViewById(R.id.activity_change_frame_2);


        editMobileNumber.addTextChangedListener(new AbsListView(getApplicationContext()) {
            @Override
            public ListAdapter getAdapter() {
                return null;
            }

            @Override
            public void setSelection(int position) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

                if(editMobileNumber.getText().length() > 0)
                    editMobileNumber.setGravity(Gravity.LEFT | Gravity.CENTER);
                else
                    editMobileNumber.setGravity(Gravity.RIGHT | Gravity.CENTER);
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editMobileNumber.getText().length() > 0){
                    frame1.setVisibility(View.INVISIBLE);
                    frame2.setVisibility(View.VISIBLE);
                }
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editRequestCode.getText().length() > 0){
                    Intent intent = new Intent(ChangeStateActivity.this , FirstContentActivity.class);
                    startActivity(intent);
                }
            }
        });

        checkNewAnswerCount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_all_imageBack_relative:{
                onBackPressed();
                break;
            }
            case R.id.toolbar_all_imageMessage_relative:{
                Intent in = new Intent(ChangeStateActivity.this , MessagingActivity.class);
                startActivity(in);
                break;
            }

        }
    }

    private void checkNewAnswerCount() {
        newAnswerCount = sharedPreferencesHome.getInt(UNREAD_ANSWERS , 0);
        if(newAnswerCount > 0){
            tvNewAnswerCount.setVisibility(View.VISIBLE);
            ivNewAnswerCountBack.setVisibility(View.VISIBLE);
            tvNewAnswerCount.setText(newAnswerCount + "");
        }else {
            tvNewAnswerCount.setVisibility(View.INVISIBLE);
            ivNewAnswerCountBack.setVisibility(View.INVISIBLE);
        }
    }

    //    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if(frame2.getVisibility() == View.VISIBLE){
            frame2.setVisibility(View.INVISIBLE);
            frame1.setVisibility(View.VISIBLE);
        }else
            super.onBackPressed();
    }
}
