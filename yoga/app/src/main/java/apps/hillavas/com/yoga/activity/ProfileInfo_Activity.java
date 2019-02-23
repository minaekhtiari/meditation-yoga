package apps.hillavas.com.yoga.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hillavas.filemanaging.classes.FileForUpload;
import com.hillavas.filemanaging.helpers.FileManagerHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.data.models.Profile;
import apps.hillavas.com.yoga.data.models.ResultJsonBoolean;
import apps.hillavas.com.yoga.data.models.ResultUploadFileResponse;
import apps.hillavas.com.yoga.classes.tools.helpers.RetrofitFactory;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileInfo_Activity extends AppCompatActivity implements View.OnClickListener{

    private static final int CAMERA_REQUEST = 1888;
    public static final String GUID="GUID";
    public static final String COMPELETE_REGISTER="COMPELETE_REGISTER";
    public static final String NAME="NAME";
    public static final String SEX="SEX";
    public static final String WEIGHT="WEIGHT";
    public static final String PICTURE_PROFILE_ADDRESS="PICTURE_PROFILE_ADDRESS";
    public static final String IS_IRANCELL="IS_IRANCELL";
    public static final String IS_HAMRAHAVAL="IS_HAMRAHAVAL";

    SharedPreferences sharedPreferencesHome;
    Button btnAddAcount;
    LinearLayout linearImageMale;
    LinearLayout linearImageFemale;
    boolean sex = false;
    ImageView imageFemale;
    ImageView imageMale;
    TextView tvMale;
    TextView tvFemale;
    Vibrator vibrator;
    String token;
    EditText editName;
    EditText editWeight;
    int weight = 0;
    CircleImageView profileImage;
    private Bitmap bmpImage = null;
    private String fileUrl = null ;
    private String fileAddress = null ;
    String purchasToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info_);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //todo
        token = sharedPreferencesHome.getString(GUID , "");

        purchasToken = sharedPreferencesHome.getString("PurchaseToken", "");

        linearImageMale = (LinearLayout) findViewById(R.id.fragment_register_linear_male);
        linearImageFemale = (LinearLayout) findViewById(R.id.fragment_register_linear_female);
        imageMale = (ImageView) findViewById(R.id.fragment_register_image_male);
        imageFemale = (ImageView) findViewById(R.id.fragment_register_image_female);
        linearImageMale.setOnClickListener(this);
        linearImageFemale.setOnClickListener(this);
        tvMale = (TextView) findViewById(R.id.fragment_register_text_male);
        tvFemale = (TextView) findViewById(R.id.fragment_register_text_female);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        editName = (EditText) findViewById(R.id.fragment_profile_insertion_editName);
        editWeight = (EditText) findViewById(R.id.fragment_profile_insertion_editWeight);
        btnAddAcount = (Button) findViewById(R.id.fragment_register_Btn_register);
    //    profileImage = (CircleImageView) findViewById(R.id.profile_image);

//        profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            }
//        });

        btnAddAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regexStr = "^[0-9]*$";
                if(!editWeight.getText().toString().trim().matches(regexStr))
                    return;

                if(editName.length()<=0) {
                    Toast.makeText(ProfileInfo_Activity.this, R.string.errorNameOrFamily, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editWeight.length()<=0) {
                    Toast.makeText(ProfileInfo_Activity.this, "لطفا وزن خود را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }else
                    weight = Integer.valueOf(editWeight.getText().toString());
                if(weight < 20) {
                    Toast.makeText(ProfileInfo_Activity.this, "لطفا وزن خود را صحیح وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }

                final Profile profile = new Profile();
                profile.setToken(token);
                profile.setSex(sex);
                profile.setFirstName(editName.getText().toString());
                profile.setLastName(editName.getText().toString());
                profile.setWeight(Integer.valueOf(editWeight.getText().toString()));

                btnAddAcount.setEnabled(false);
//                if(sharedPreferencesHome.getBoolean(IS_HAMRAHAVAL,true)){
                RetrofitFactory.getRetrofitClient().updateProfileInfo(profile).enqueue(new Callback<ResultJsonBoolean>() {
                    @Override
                    public void onResponse(Call<ResultJsonBoolean> call, Response<ResultJsonBoolean> response) {
                        if(response.isSuccessful() && response.body().isResult()){
                            sharedPreferencesHome.edit().putBoolean(COMPELETE_REGISTER , true).commit();
                            sharedPreferencesHome.edit().putBoolean(SEX , true).commit();
                            sharedPreferencesHome.edit().putString(NAME , profile.getFirstName()).commit();
                            sharedPreferencesHome.edit().putInt(WEIGHT , profile.getWeight()).commit();
                            Intent intent = new Intent(ProfileInfo_Activity.this , FirstContentActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultJsonBoolean> call, Throwable t) {
                        Toast.makeText(ProfileInfo_Activity.this, "خطا", Toast.LENGTH_SHORT).show();
                        btnAddAcount.setEnabled(true);
                    }
                });
               // }
//                else if(sharedPreferencesHome.getBoolean(IS_IRANCELL,true)){
//
//
//                        CharkhonehHttpFactory.getRetrofitClient().IsSubscribe("apps.hillavas.com.sexual",
//                                "premium_test", purchasToken, "9d568991-f5cf-386f-a740-9e66fd992588").enqueue(new Callback<IsSubMtn>() {
//                            @Override
//                            public void onResponse(Call<IsSubMtn> call, Response<IsSubMtn> response) {
//                                if (response.body().getAutoRenewing()) {
//                                    Long expiryTimeMillis = response.body().getExpiryTimeMillis()/1000L;
//                                    Long currentTime = System.currentTimeMillis() / 1000L;
//
//                                    if (expiryTimeMillis >= currentTime) {
//                                        //todo
//                                        //complete_register be true
//
////                                        Fragment_Home fragment_home = new Fragment_Home();
////                                        Bundle bundleHome = new Bundle();
////                                        bundleHome.putInt("arg1", 11);
////                                        bundleHome.putString("Token", token);
////                                        fragment_home.setArguments(bundleHome);
////                                        new FragmentHelper(fragment_home,
////                                                R.id.frameLayout_base,
////                                                getActivity().getSupportFragmentManager()
////                                        ).replace(false);
//
//
//                                    }
//                                }
//
//                                else {
//                                    //getActivity().finish();
//
//                                //    sharedPreferencesHome.edit().putBoolean(REGISTERED , false).commit();
//                                  //  startActivity(new Intent(getActivity(), HOME.class));
//
//
//                                }
                       //     }

//                            @Override
//                            public void onFailure(Call<IsSubMtn> call, Throwable t) {
//
//                            }
//                        });

                 //   }


            }
        });
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_register_linear_female:{
                if(sex == true) {
                    maleFemaleChanger();
                   //     if(sharedPreferencesHome.getString(PICTURE_PROFILE_ADDRESS , "").length() <= 0)
                       // profileImage.setImageResource(R.drawable.person_woman);
                }
                vibrator.vibrate(65);
                break;
            }
            case R.id.fragment_register_linear_male:{
                if(sex == false) {
                    maleFemaleChanger();
                   // if(sharedPreferencesHome.getString(PICTURE_PROFILE_ADDRESS , "").length() <= 0)
                     //   profileImage.setImageResource(R.drawable.person_man);
                }
                vibrator.vibrate(65);
                break;
            }
        }
    }

    private void maleFemaleChanger(){
        if(!sex) {
            imageMale.setImageResource(R.drawable.male_red);
            imageFemale.setImageResource(R.drawable.female_gray);
            tvMale.setTextColor(getResources().getColor(R.color.red));
            tvFemale.setTextColor(getResources().getColor(R.color.gray));
            sex = true;
        }else{
            imageMale.setImageResource(R.drawable.male_gray);
            imageFemale.setImageResource(R.drawable.female_red);
            tvMale.setTextColor(getResources().getColor(R.color.gray));
            tvFemale.setTextColor(getResources().getColor(R.color.red));
            sex = false;
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    class TaskUploadFile extends AsyncTask<FileForUpload , Void , JSONObject> {

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
                    .into(profileImage);

        }
    }

}
