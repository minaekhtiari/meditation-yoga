package fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.pazandish.sdk.AuthorizationUtil;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.ProfileInfo_Activity;
import apps.hillavas.com.yoga.R;
import classes.models.Level;
import classes.models.RequestMobileGiverModel;
import classes.models.ResultJson;
import classes.models.ResultJsonInteger;
import classes.models.SignUpMemberModel;
import classes.tools.HideSoftKeyboards;
import classes.tools.helpers.RetrofitFactory;
import factories.FragmentHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_Register extends Fragment {

    public static final String MOBILE_NUMBER="MOBILE_NUMBER";
    public static final String FIRST_RESULT_CODE="FIRST_RESULT_CODE";
    public static final String PASSWORD="PASSWORD";
    public static final String GUID="GUID";
    SharedPreferences sharedPreferencesHome;

    EditText editMobileNumber;

    Button btnRegister;

    Vibrator vibrator;
    Typeface tfIranSans;
    int resultCode = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        AuthorizationUtil.checkAuthorization(getActivity(),"yoga-04f3aa95-f422-417b-9b9b-94707dde39bc");


        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        tfIranSans = Typeface.createFromAsset(getContext().getAssets() , "fonts/iransans.ttf");
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        editMobileNumber = (EditText) getActivity().findViewById(R.id.fragment_register_EditMobile);
        btnRegister = (Button) getActivity().findViewById(R.id.fragment_register_Btn_register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regexStr = "^[0-9]*$";

                if(!editMobileNumber.getText().toString().trim().matches(regexStr))
                    return;

                if(editMobileNumber.getText().length() > 0) {
                    btnRegister.setEnabled(false);
                    vibrator.vibrate(65);
                    final RequestMobileGiverModel mobileGiverModel = new RequestMobileGiverModel();
                    mobileGiverModel.setMobileNumber(Long.valueOf(editMobileNumber.getText().toString()));
                    mobileGiverModel.setChanel(8);

                    RetrofitFactory.getRetrofitClient().registerOtp(mobileGiverModel).enqueue(new Callback<ResultJsonInteger>() {
                        @Override
                        public void onResponse(Call<ResultJsonInteger> call, Response<ResultJsonInteger> response) {
                        if (response != null && response.body().isIsSuccessfull()) {

                            if(response.body().getResult() == -1){
                                sharedPreferencesHome.edit().putLong(MOBILE_NUMBER , mobileGiverModel.getMobileNumber()).commit();
                                sharedPreferencesHome.edit().putString(GUID, response.body().getMessage()).commit();
                                Intent intent = new Intent(getActivity(), ProfileInfo_Activity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }
                            resultCode = response.body().getResult();
                            sharedPreferencesHome.edit().putLong(MOBILE_NUMBER , mobileGiverModel.getMobileNumber()).commit();
                            sharedPreferencesHome.edit().putInt(FIRST_RESULT_CODE , response.body().getResult()).commit();
                        try {
                        new FragmentHelper(new Fragment_CodeRequest(),
                        R.id.frameLayout_base,
                        getActivity().getSupportFragmentManager()
                        ).replace(false);
                        }catch (Exception e){}
                        }else{
                        Toast.makeText(getActivity(), "لطفا چند لحظه دیگر سعی نمائید", Toast.LENGTH_SHORT).show();
                        btnRegister.setEnabled(false);
                        }

                        }

                        @Override
                        public void onFailure(Call<ResultJsonInteger> call, Throwable t) {
                        if(getActivity() != null) {
                            Toast.makeText(getActivity(), "لطفا چند لحظه دیگر سعی نمائید", Toast.LENGTH_SHORT).show();
                            btnRegister.setEnabled(true);
                        }
                        }

                        }
                    );



                }
            }
        });

//        editMobileNumber.addTextChangedListener(new AbsListView(getActivity()) {
//            @Override
//            public ListAdapter getAdapter() {
//                return null;
//            }
//
//            @Override
//            public void setSelection(int position) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                super.onTextChanged(s, start, before, count);
//
//                if(editMobileNumber.getText().length() > 0)
//                    editMobileNumber.setGravity(Gravity.LEFT | Gravity.CENTER);
//                else
//                    editMobileNumber.setGravity(Gravity.RIGHT | Gravity.CENTER);
//            }
//        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

}




