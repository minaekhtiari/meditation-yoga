package apps.hillavas.com.meditation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerView_record_adapter;
import classes.models.Record;
import classes.tools.helpers.RetrofitFactory;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ContentActivity extends AppCompatActivity {


    private static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String GUID="GUID";
    public static final String NAME="NAME";
    public static final String SEX="SEX";
    public static final String WEIGHT="WEIGHT";
    public static final String PICTURE_PROFILE_ADDRESS="PICTURE_PROFILE_ADDRESS";
    RelativeLayout relativeToolbarSetting;
    RelativeLayout relativeToolbarBack;
    DrawerLayout drawerLayout;
    int categoryId = 0;
    SharedPreferences sharedPreferencesHome;
    String token = null;
    CircleImageView ivProfilePicture;
    TextView tvNameProfileInfo;
    TextView tvWeightProfileInfo;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        progressBar = (ProgressBar) findViewById(R.id.fragment_content_progressbar);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_content_drawerLayout);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferencesHome.getString(GUID, "");
        if(getIntent() != null){
            if(getIntent().getExtras().containsKey(CATEGORY_ID)){
                categoryId  = getIntent().getIntExtra(CATEGORY_ID , 0);
            }
        }
        tvNameProfileInfo = (TextView) findViewById(R.id.fragment_personalInfo_text_name);
        tvWeightProfileInfo = (TextView) findViewById(R.id.fragment_personalInfo_text_weight);
        ivProfilePicture = (CircleImageView) findViewById(R.id.profile_image);

        relativeToolbarSetting = (RelativeLayout) findViewById(R.id.toolbar_all_imageMenu_relativeSetting);
        relativeToolbarSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        relativeToolbarBack = (RelativeLayout) findViewById(R.id.toolbar_all_imageMenu_relativeBack);
        relativeToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                tvNameProfileInfo.setText(sharedPreferencesHome.getString(NAME , ""));
                tvWeightProfileInfo.setText(sharedPreferencesHome.getInt(WEIGHT , 0)+"");
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

        if(categoryId > 0){
            new TaskLoadContents().execute(categoryId);
        }
    }


    class TaskLoadContents extends AsyncTask<Integer,Void,List<Record>> {

        List<Record> records = new ArrayList<>();
        @Override
        protected List<Record> doInBackground(Integer... params) {

            try {
                if (RetrofitFactory.getRetrofitClient().getContents(token,categoryId,1,100).execute().body().isIsSuccessfull())
                    records = RetrofitFactory.getRetrofitClient().getContents(token,categoryId,1,100).execute().body().getResult().getRecords();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return records;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Record> records) {
            super.onPostExecute(records);
            if(records != null && records.size() > 0){
                recyclerView = (RecyclerView) findViewById(R.id.content_recycler_content);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                RecyclerView_record_adapter records_adapter = new RecyclerView_record_adapter(getApplicationContext() , records , token);
                recyclerView.setAdapter(records_adapter);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
