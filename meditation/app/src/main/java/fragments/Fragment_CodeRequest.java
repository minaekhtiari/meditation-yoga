package fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import apps.hillavas.com.meditation.ProfileInfo_Activity;
import apps.hillavas.com.meditation.R;
import classes.models.OtpConfirmation;
import classes.models.ResultJson;
import classes.tools.helpers.RetrofitFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_CodeRequest extends Fragment {

    public static final String MOBILE_NUMBER="MOBILE_NUMBER";
    public static final String FIRST_RESULT_CODE="FIRST_RESULT_CODE";
    public static final String GUID="GUID";
    SharedPreferences sharedPreferencesHome;

    EditText editCode;
    Button btnCodeRequestSender;
    String token = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code_request, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editCode = (EditText) getActivity().findViewById(R.id.fragment_codeRequest_editCodeRequest);
        btnCodeRequestSender = (Button) getActivity().findViewById(R.id.fragment_register_Btn_register);
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                getActivity().INPUT_METHOD_SERVICE);
        imm.showSoftInput(editCode, 0);

        btnCodeRequestSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regexStr = "^[0-9]*$";
                if(!editCode.getText().toString().trim().matches(regexStr))
                    return;

                if(editCode.getText().length() > 0) {

                    sharedPreferencesHome.edit().putString(GUID, "509FDAF9-4F35-45D3-AAD2-B0E196ACDBB1").commit();
                    Intent intent = new Intent(getActivity(), ProfileInfo_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

//                        OtpConfirmation otpConfirmation = new OtpConfirmation();
//                        otpConfirmation.setMobileNumber(sharedPreferencesHome.getLong(MOBILE_NUMBER , 0));
//                        otpConfirmation.setRequestId(sharedPreferencesHome.getInt(FIRST_RESULT_CODE , 0));
//                        otpConfirmation.setPin(Integer.valueOf(editCode.getText().toString()));
//                        if(otpConfirmation.getMobileNumber() > 0 && otpConfirmation.getPin() > 0 && otpConfirmation.getRequestId() > 0 ) {
//                            RetrofitFactory.getRetrofitClient().confirmOtp(otpConfirmation).enqueue(new Callback<ResultJson>() {
//                                @Override
//                                public void onResponse(Call<ResultJson> call, Response<ResultJson> response) {
//                                    if (response != null && response.body() != null &&response.body().isIsSuccessfull()) {
//                                        token = response.body().getResult();
//                                        if (token != null && token.length() > 0) {
//                                            sharedPreferencesHome.edit().putString(GUID, token).commit();
//                                            Intent intent = new Intent(getActivity(), ProfileInfo_Activity.class);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                            startActivity(intent);
//                                            btnCodeRequestSender.setEnabled(true);
//                                        }
//                                    }else {
//                                        Toast.makeText(getActivity(), "???? ???????????? ?????? .", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                                @Override
//                                public void onFailure(Call<ResultJson> call, Throwable t) {
//                                }
//                            });
//                        }
                }
            }
        });

//        btnCodeRequestSender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    final RequestCodeGiverModel requestCodeGiverModel = new RequestCodeGiverModel();
//                    requestCodeGiverModel.setMobileNumber(sharedPreferencesHome.getLong(MOBILE_NUMBER , 0));
//                try {
//                    requestCodeGiverModel.setCode(Long.valueOf(editCode.getText().toString()));
//                    RetrofitFactory.getRetrofitClient().requestCodeGiver(requestCodeGiverModel).enqueue(new Callback<ResultJson>() {
//                        @Override
//                        public void onResponse(Call<ResultJson> call, Response<ResultJson> response) {
//                            if (response != null) {
//                                if(!response.body().isIsSuccessfull()){
//                                    Toast.makeText(getActivity(), R.string.incorrectCode, Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                                if (response.body().getResult().length() > 0) {
//                                    sharedPreferencesHome.edit().putString(GUID, response.body().getResult()).commit();
//                                    Intent intent = new Intent(getActivity() , FirstContentActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                }
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<ResultJson> call, Throwable t) {
//
//                        }
//                    });
//                }catch (Exception e){
//                    Toast.makeText(getActivity(), R.string.incorrectCode, Toast.LENGTH_SHORT).show();
//                }
//                }
//        });


    }

}
