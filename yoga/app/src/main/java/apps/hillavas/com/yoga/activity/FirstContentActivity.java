package apps.hillavas.com.yoga.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.activity.fragments.Fragment_content_level1;
import apps.hillavas.com.yoga.activity.fragments.Fragment_contents;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FirstContentActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String PICTURE_PROFILE_ADDRESS="PICTURE_PROFILE_ADDRESS";
    public static final String NAME="NAME";
    public static final String TRAINING_CODE="TRAINING_CODE";
    public static final String SEX="SEX";
    public static final String WEIGHT="WEIGHT";
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String LEVEL_ID = "LEVEL_ID";
    private static final String CATEGORYID_ID = "CATEGORYID_ID";
    private static final String HAS_CHILD = "HAS_CHILD";
    private static final String SELECTED_CATEGORYID_ID = "SELECTED_CATEGORYID_ID";
    public static final String SENT_MESSAGE="SENT_MESSAGE";
    public static final String GUID="GUID";
    public static final String UNREAD_ANSWERS="UNREAD_ANSWERS";
    public static final String IS_IRANCELL="IS_IRANCELL";
    public static final String IS_HAMRAHAVAL="IS_HAMRAHAVAL";
    SharedPreferences sharedPreferencesHome;
    String token = null;
    String title = "";
    int id = 0;
    int newAnswerCount = 0;
    Vibrator vibrator;

    ViewPager viewPager;
    RelativeLayout relativeToolbarSetting;
    RelativeLayout relativeToolbarPerson;
    DrawerLayout drawerLayout;
    ImageView ivProfilePicture;
    String name;
    int weight;
    ImageView ivToolbarBtnPerson;

    TextView tvName;
    TextView tvWeight;

    FrameLayout frameBase1,frameBase2;
    TextView tvNavBtnHome;
    TextView tvNavBtnAmar;
    TextView tvNavBtnAsli;
    TextView tvNavBtnApp;
    TextView tvNavBtnExit, navUnsubscribeTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_content);
//        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        frameBase1 = (FrameLayout) findViewById(R.id.firstContentActivity_frameBase);
        frameBase2 = (FrameLayout) findViewById(R.id.firstContentActivity_frameBase2);
        //todo
        token = sharedPreferencesHome.getString(GUID, "");
        name = sharedPreferencesHome.getString(NAME, "");
        weight = sharedPreferencesHome.getInt(WEIGHT, 0);
        tvName = (TextView) findViewById(R.id.fragment_personalInfo_text_name);
        tvWeight = (TextView) findViewById(R.id.fragment_personalInfo_text_weight);
        ivProfilePicture = (ImageView) findViewById(R.id.profile_image);
        tvNavBtnHome = (TextView) findViewById(R.id.fragment_personalInfo_text_home);
        tvNavBtnAmar = (TextView) findViewById(R.id.fragment_personalInfo_text_amar);
        tvNavBtnAsli = (TextView) findViewById(R.id.fragment_personalInfo_text_harakatAsli);
        tvNavBtnApp = (TextView) findViewById(R.id.fragment_personalInfo_text_app);
        tvNavBtnExit = (TextView) findViewById(R.id.fragment_personalInfo_text_exit);
        navUnsubscribeTxt=findViewById(R.id.fragment_personalInfo_sign_out_irancell);

        if(sharedPreferencesHome.getBoolean(IS_HAMRAHAVAL,true)){

            navUnsubscribeTxt.setVisibility(View.GONE);
        }

        tvNavBtnHome.setOnClickListener(this);
        tvNavBtnAmar.setOnClickListener(this);
        tvNavBtnAsli.setOnClickListener(this);
        tvNavBtnApp.setOnClickListener(this);
        tvNavBtnExit.setOnClickListener(this);
        ivProfilePicture.setOnClickListener(this);
        navUnsubscribeTxt.setOnClickListener(this);


        ((RelativeLayout)findViewById(R.id.fragment_personalInfo_relativeHeader)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstContentActivity.this, CheckBalanceActivity.class);
                startActivity(intent);
            }
        });

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstContentActivity.this, CheckBalanceActivity.class);
                startActivity(intent);
            }
        });

        if(sharedPreferencesHome.getString(PICTURE_PROFILE_ADDRESS , "").length() > 0){
            Glide.with(getApplicationContext())
                    .load(sharedPreferencesHome.getString(PICTURE_PROFILE_ADDRESS, ""))
                    .asBitmap()
                    .override(424, 240)
                    .centerCrop()
                    .into(ivProfilePicture);
        }else {
            if(sharedPreferencesHome.getBoolean(SEX , false)){
                ivProfilePicture.setImageResource(R.drawable.person_man);
            }else {
                ivProfilePicture.setImageResource(R.drawable.person_woman);
            }
        }
        tvName.setText(name);
        tvWeight.setText(weight+"");
        title = getIntent().getStringExtra("MENU_ITEM");
        id = getIntent().getIntExtra("MENU_ID",0);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_menu_drawerLayout);
        relativeToolbarPerson = (RelativeLayout) findViewById(R.id.toolbar_home_imageMenu_relativePerson);
        relativeToolbarPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstContentActivity.this, CheckBalanceActivity.class);
                startActivity(intent);
            }
        });

        relativeToolbarSetting = (RelativeLayout) findViewById(R.id.toolbar_home_imageMenu_relativeSetting);
        relativeToolbarSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                0,0 ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                tvName.setText(sharedPreferencesHome.getString(NAME , ""));
                tvWeight.setText(sharedPreferencesHome.getInt(WEIGHT , 0)+"");

                if(sharedPreferencesHome.getString(PICTURE_PROFILE_ADDRESS , "").length() > 0){
                    Glide.with(getApplicationContext())
                            .load(sharedPreferencesHome.getString(PICTURE_PROFILE_ADDRESS, ""))
                            .asBitmap()
                            .override(424, 240)
                            .centerCrop()
                            .into(ivProfilePicture);
                }else {
                    if(sharedPreferencesHome.getBoolean(SEX , false)){
                        ivProfilePicture.setImageResource(R.drawable.person_man);
                    }else {
                        ivProfilePicture.setImageResource(R.drawable.person_woman);
                    }
                }
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.firstContentActivity_frameBase , new Fragment_content_level1()).commit();

        Fragment_contents fragment_contents = new Fragment_contents();
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID , sharedPreferencesHome.getInt(CATEGORY_ID, 0 ));
        fragment_contents.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.firstContentActivity_frameBase2 , fragment_contents).commit();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_personalInfo_text_home:{
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.firstContentActivity_frameBase , new Fragment_content_level1()).commit();
                frameBase1.setVisibility(View.VISIBLE);
                frameBase2.setVisibility(View.INVISIBLE);
                //resetRelativeBacks();
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            }
            case R.id.fragment_personalInfo_text_amar:{
                Intent intent = new Intent(FirstContentActivity.this, CheckBalanceActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            }
            case R.id.fragment_personalInfo_text_harakatAsli:{
                drawerLayout.closeDrawer(GravityCompat.END);
                Intent intent = new Intent(this,TrainingActivity.class);
                intent.putExtra(TRAINING_CODE , 1);
                startActivity(intent);
                break;
            }
            case R.id.fragment_personalInfo_text_app:{
                Intent intent = new Intent(this,TrainingActivity.class);
                intent.putExtra(TRAINING_CODE , 2);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            }
            case R.id.fragment_personalInfo_text_exit:{
                drawerLayout.closeDrawer(GravityCompat.END);
                finish();
                break;
            }

            case R.id.fragment_personalInfo_sign_out_irancell:{
                Intent intent = new Intent(this,UnsubscribeActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
            return;
        }
        if(frameBase1.getVisibility() == View.VISIBLE)
            super.onBackPressed();
        else {
            frameBase1.setVisibility(View.VISIBLE);
            frameBase2.setVisibility(View.INVISIBLE);
            try {
                resetRelativeBacks();
            }catch (Exception e){}
        }
    }

    private void resetRelativeBacks() {
        frameBase1.findViewById(R.id.fragment_yoga_category_badani).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        frameBase1.findViewById(R.id.fragment_yoga_category_asana).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        frameBase1.findViewById(R.id.fragment_yoga_category_paksazi).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        frameBase1.findViewById(R.id.fragment_yoga_category_sikl).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        frameBase1.findViewById(R.id.fragment_yoga_category_zen).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        frameBase1.findViewById(R.id.fragment_yoga_category_amadegi).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
}
