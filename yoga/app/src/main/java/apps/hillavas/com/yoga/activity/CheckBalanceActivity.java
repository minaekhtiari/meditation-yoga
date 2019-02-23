package apps.hillavas.com.yoga.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hillavas.filemanaging.classes.FileForUpload;
import com.hillavas.filemanaging.helpers.FileManagerHelper;
import com.robinhood.spark.SparkView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.adapters.SparkView_Adapter;
import apps.hillavas.com.yoga.data.models.JsonTotalCalloryAndPoints;
import apps.hillavas.com.yoga.data.models.ResultCalloryHistoryDaily;
import apps.hillavas.com.yoga.data.models.ResultTotalCalloryAndPoint;
import apps.hillavas.com.yoga.data.models.ResultUploadFileResponse;
import apps.hillavas.com.yoga.classes.tools.RecordCalloryMounthly;
import apps.hillavas.com.yoga.classes.tools.helpers.RetrofitFactory;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CheckBalanceActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String GUID = "GUID";
    private static final int CAMERA_REQUEST = 1888;
    public static final String NAME="NAME";
    public static final String SEX="SEX";
    public static final String WEIGHT="WEIGHT";
    public static final String PICTURE_PROFILE_ADDRESS="PICTURE_PROFILE_ADDRESS";
    TextView tvWeight;
    ImageView ivMinus;
    ImageView ivPlus;
    EditText editName;
    SharedPreferences sharedPreferencesHome;
    int weight = 0;
    RelativeLayout relativeBack;
    SparkView sparkView;
    CircleImageView imageProfile;
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    private Bitmap bmpImage = null;
    private String fileUrl = null ;
    private String fileAddress = null ;
    String token = null;

    TextView tvExamTime;
    TextView tvCalloryToday;
    TextView tvScoreTotally;
    ProgressBar progressBar;

    ProgressBar progressBarCallory;
    ProgressBar progressBarExamTime;
    ProgressBar progressBarTotalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_balances);
        progressBarCallory = (ProgressBar) findViewById(R.id.fragment_check_balances_progressCallory);
        progressBarExamTime = (ProgressBar) findViewById(R.id.fragment_check_balances_progressExamTime);
        progressBarTotalScore = (ProgressBar) findViewById(R.id.fragment_check_balances_progressTotalScore);

        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferencesHome.getString(GUID, "");
        relativeBack = (RelativeLayout) findViewById(R.id.relativeBack);
        sparkView = (SparkView) findViewById(R.id.fragment_check_balance_sparkview);
        tvExamTime  = (TextView) findViewById(R.id.fragment_check_balances_ExamTime);
        tvCalloryToday = (TextView) findViewById(R.id.fragment_check_balances_calories_today);
        tvScoreTotally = (TextView) findViewById(R.id.fragment_check_balances_scoreTitr);
        progressBar = (ProgressBar) findViewById(R.id.fragment_check_balance_progress);
        sparkView.setLineColor(getResources().getColor(R.color.white));
        sparkView.setLineWidth(5f);
        imageProfile = (CircleImageView) findViewById(R.id.fragment_check_balance_profile_image);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editName = (EditText) findViewById(R.id.fragment_check_balance_textName);
        tvWeight = (TextView) findViewById(R.id.fragment_check_balance_textWeight);
        ivMinus = (ImageView) findViewById(R.id.fragment_check_balance_ivMinus);
        ivPlus = (ImageView) findViewById(R.id.fragment_check_balance_ivPlus);

        ivMinus.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        weight = sharedPreferencesHome.getInt(WEIGHT , 0);
        editName.setText(sharedPreferencesHome.getString(NAME , ""));
        tvWeight.setText(weight+"");

        if(sharedPreferencesHome.getString(PICTURE_PROFILE_ADDRESS , "").length() > 0) {
            Glide.with(getApplicationContext())
                    .load(sharedPreferencesHome.getString(PICTURE_PROFILE_ADDRESS, ""))
                    .asBitmap()
                    .override(424, 240)
                    .centerCrop()
                    .into(imageProfile);
        }else
        {
            if(sharedPreferencesHome.getBoolean(SEX , false))
                imageProfile.setImageResource(R.drawable.person_man);
            else
                imageProfile.setImageResource(R.drawable.person_woman);
        }

        new TaskLoadCaloryHistoryDaily().execute();
        new TaskLoadCaloryAndTimeHistoryDaily().execute();
        new TaskLoadTotalCalloryAndPoint().execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();
            String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            FileForUpload fileForUpload = new FileForUpload();
            fileForUpload.setFilename("ProfilePicture.jpg");
            fileForUpload.setUsername("Hillavas_Youga");
            fileForUpload.setPassword("H!ll@v@s_Ug@");
            fileForUpload.setStringBase64(encodedImage);
            new TaskUploadFile().execute(fileForUpload);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_check_balance_ivMinus : {
                weight--;
                break;
            }
            case R.id.fragment_check_balance_ivPlus : {
                weight++;
                break;
            }
        }

        if(weight != Integer.valueOf(tvWeight.getText().toString()))
            tvWeight.setText(weight+"");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        try {
            weight = Integer.valueOf(tvWeight.getText().toString());
        }catch (Exception e){}

        if(weight != sharedPreferencesHome.getInt(WEIGHT , 0) || !editName.getText().toString().equals(sharedPreferencesHome.getString(NAME , ""))) {
            final Dialog dialog = new Dialog(CheckBalanceActivity.this);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setContentView(R.layout.dialog_edit_data);
            ImageView ivBtnAccept = (ImageView) dialog.findViewById(R.id.dialog_editData_image_accept);
            ImageView ivBtnCancel = (ImageView) dialog.findViewById(R.id.dialog_editData_image_cancel);

            ivBtnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferencesHome.edit().putInt(WEIGHT, weight).commit();
                    sharedPreferencesHome.edit().putString(NAME, editName.getText().toString()).commit();
                    dialog.hide();
                    Toast.makeText(CheckBalanceActivity.this, "ویرایش انجام شد", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            ivBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.hide();
                    finish();
                }
            });
            dialog.show();
        }else
            finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    class TaskUploadFile extends AsyncTask<FileForUpload , Void , JSONObject>{

        @Override
        protected JSONObject doInBackground(FileForUpload... params) {
            try {
                return FileManagerHelper.fileUpload(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null){
                Gson gson = new Gson();
                ResultUploadFileResponse resultUploadResponse = gson.fromJson(jsonObject.toString() ,ResultUploadFileResponse.class);
                fileUrl = resultUploadResponse.getFileID();
                new TaskLoadImageAddress().execute(fileUrl);

            }
        }
    }

    class TaskLoadImageAddress extends AsyncTask<String , Void , String>{

        @Override
        protected String doInBackground(String... params) {
            return FileManagerHelper.getOneFileAddress(params[0] , "image");
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fileAddress = s;
            sharedPreferencesHome.edit().putString(PICTURE_PROFILE_ADDRESS , s).commit();
            Glide.with(getApplicationContext())
                    .load(fileAddress)
                    .asBitmap()
                    .override(424,240)
                    .centerCrop()
                    .into(imageProfile);

        }
    }


    class TaskLoadCaloryHistoryDaily extends AsyncTask<Integer, Void, List<RecordCalloryMounthly>> {

        List<RecordCalloryMounthly> rc = new ArrayList<>();
        @Override
        protected List<RecordCalloryMounthly> doInBackground(Integer... params) {

            try {
                if (RetrofitFactory.getRetrofitClient().getMemberHistory(token, 1,1,1, 10).execute().body().isIsSuccessfull())
                    rc = RetrofitFactory.getRetrofitClient().getMemberHistory(token,1,1,1, 10).execute().body().getResultCalory().getRecordsCalory();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<RecordCalloryMounthly> rc) {
            super.onPostExecute(rc);
            List<Float> floats = new ArrayList<>();
            List<Float> floatsRev = new ArrayList<>();

            for(RecordCalloryMounthly r : rc){
                floats.add((float) r.getTotal() * 100);
            }

            for(int i = rc.size()-1 ; i >= 0 ; i--){
                floatsRev.add(floats.get(i));
                floatsRev.add(20f);
            }


            float[] floatArray = new float[floatsRev.size()];
            int i = 0;


            for (Float f : floatsRev) {
                floatArray[i++] = (f != null ? f : Float.NaN); // Or whatever default you want.
            }

//            sparkView.setAdapter(new SparkView_Adapter(new float[]{890f,425f,20f,142f,20f,175f,60f,1435f,750f,405f,20f,99f}));
            sparkView.setAdapter(new SparkView_Adapter(floatArray));

            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    class TaskLoadCaloryAndTimeHistoryDaily extends AsyncTask<Integer, Void, List<ResultCalloryHistoryDaily>> {

        List<ResultCalloryHistoryDaily> rc = new ArrayList<>();
        @Override
        protected List<ResultCalloryHistoryDaily> doInBackground(Integer... params) {

            try {
                if (RetrofitFactory.getRetrofitClient().getCaloryTimeHistoryDaily(token).execute().body().isIsSuccessfull())
                    rc = RetrofitFactory.getRetrofitClient().getCaloryTimeHistoryDaily(token).execute().body().getResultCalloryHistoryDaily();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarCallory.setVisibility(View.VISIBLE);
            progressBarExamTime.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<ResultCalloryHistoryDaily> rc) {
            super.onPostExecute(rc);

            int totalCalory = 0;
            int totalTime = 0;


            if(rc != null)
            for(ResultCalloryHistoryDaily r : rc){
                totalCalory += r.getTotalCalory();
                totalTime += r.getContentLength();
            }


            tvCalloryToday.setText(totalCalory + " K ");
            tvExamTime.setText(totalTime + "");

            progressBarCallory.setVisibility(View.INVISIBLE);
            progressBarExamTime.setVisibility(View.INVISIBLE);
        }
    }

    class TaskLoadTotalCalloryAndPoint extends AsyncTask<Integer, Void, ResultTotalCalloryAndPoint> {

        ResultTotalCalloryAndPoint rc = null;
        @Override
        protected ResultTotalCalloryAndPoint doInBackground(Integer... params) {

//            try {
//                if (RetrofitFactory.getRetrofitClient().getTotalCalloryAndPoint(token).execute().body().isIsSuccessfull())
//                    rc = RetrofitFactory.getRetrofitClient().getTotalCalloryAndPoint(token).execute().body().getResultTotalCalloryAndPoint();
//            } catch (IOException e) {
//                e.printStackTrace();
          //  }

            RetrofitFactory.getRetrofitClient().getTotalCalloryAndPoint(token).enqueue(new Callback<JsonTotalCalloryAndPoints>() {
                @Override
                public void onResponse(Call<JsonTotalCalloryAndPoints> call, Response<JsonTotalCalloryAndPoints> response) {
                    if(response.body().isIsSuccessfull()){
                        rc=response.body().getResultTotalCalloryAndPoint();
                    }
                    else {
                        Toast.makeText(CheckBalanceActivity.this,"اینجااااااا",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<JsonTotalCalloryAndPoints> call, Throwable t) {
                   Toast.makeText(CheckBalanceActivity.this,"دوباره سعی کنید",Toast.LENGTH_LONG).show();
                }
            });

           return rc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarTotalScore.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ResultTotalCalloryAndPoint resultTotalCalloryAndPoint) {
            super.onPostExecute(resultTotalCalloryAndPoint);
            if(resultTotalCalloryAndPoint != null)
                tvScoreTotally.setText(resultTotalCalloryAndPoint.getTotalPoint() + "");
            else
                tvScoreTotally.setText("0");
            progressBarTotalScore.setVisibility(View.INVISIBLE);
        }
    }
}





