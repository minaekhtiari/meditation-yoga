package apps.hillavas.com.yoga.activity.sign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.activity.FirstContentActivity;
import apps.hillavas.com.yoga.classes.tools.helpers.RetrofitFactory;
import apps.hillavas.com.yoga.data.models.OtpResultJson;
import apps.hillavas.com.yoga.data.models.ResultJson;
import apps.hillavas.com.yoga.data.models.ResultJsonMemberSignUp;
import apps.hillavas.com.yoga.data.models.SubscribeConfirmModel;
import apps.hillavas.com.yoga.data.remote.OtpApiFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class FragmentSubscribeConfirm extends Fragment {

    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String PASSWORD = "PASSWORD";
    public static final String GUID = "GUID";
    public static final String TRANSACTIONID = "TRANSACTIONID";
    public static final String SUBSCRIBEDUSER = "SubscribedUser";


    SharedPreferences sharedPreferencesHome;

    EditText editCode;
    Button btnConfirm;
    TextView btnBackToSubscribe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otp_confirm, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editCode = getActivity().findViewById(R.id.fragment_codeRequest_editCodeRequest);
        btnConfirm = getActivity().findViewById(R.id.fragment_register_Btn_register);
        btnBackToSubscribe = getActivity().findViewById(R.id.btn_back_to_subscribe);

        btnBackToSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editCode.getText().length() == 4) {
                    btnConfirm.setEnabled(false);
                    final SubscribeConfirmModel subscribeConfirmModel = new SubscribeConfirmModel();
                    //requestCodeGiverModel.setMobileNumber(sharedPreferencesHome.getLong(MOBILE_NUMBER, 0));
                    subscribeConfirmModel.setTransactionId(sharedPreferencesHome.getString(TRANSACTIONID, ""));
                    subscribeConfirmModel.setPin(String.valueOf(editCode.getText().toString()));


                    if (sharedPreferencesHome.getBoolean(SUBSCRIBEDUSER, false)) {//is subscribed

                        sharedPreferencesHome.edit().putBoolean(SUBSCRIBEDUSER, false).commit();


                        OtpApiFactory.getOtpClient().subscribeConfirmViaCode(subscribeConfirmModel).enqueue(new Callback<OtpResultJson>() {
                            @Override
                            public void onResponse(Call<OtpResultJson> call, Response<OtpResultJson> response) {

                                if (response != null) {
                                    if (!response.body().isIsSuccessfull()) {
                                        btnConfirm.setEnabled(true);

                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    memberSignUpFotReciveToken(response.body().getResult());
                                }

                            }

                            @Override
                            public void onFailure(Call<OtpResultJson> call, Throwable t) {
                                Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();

                                btnConfirm.setEnabled(true);

                            }
                        });

                    } else {

                        OtpApiFactory.getOtpClient().subscribeConfirm(subscribeConfirmModel).enqueue(new Callback<OtpResultJson>() {
                            @Override
                            public void onResponse(Call<OtpResultJson> call, Response<OtpResultJson> response) {

                                if (response != null) {
                                    if (!response.body().isIsSuccessfull()) {
                                        btnConfirm.setEnabled(true);

                                        Toast.makeText(getActivity(), R.string.incorrectCode, Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    memberSignUpFotReciveToken(response.body().getResult());
                                }

                            }

                            @Override
                            public void onFailure(Call<OtpResultJson> call, Throwable t) {
                                Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();

                                btnConfirm.setEnabled(true);

                            }
                        });


                    }


                } else {
                    Toast.makeText(getActivity(), R.string.incorrectCode, Toast.LENGTH_SHORT).show();

                }

            }

        });


    }



    void memberSignUpFotReciveToken(String phoneNumber) {

        RetrofitFactory.getRetrofitClient().memberSignUp(phoneNumber).enqueue(new Callback<ResultJsonMemberSignUp>() {
            @Override
            public void onResponse(Call<ResultJsonMemberSignUp> call, Response<ResultJsonMemberSignUp> response) {

                btnConfirm.setEnabled(true);

                if (response.isSuccessful()) {
                    sharedPreferencesHome.edit().putString(GUID, response.body().getMemberSignUp().getToken()).commit();
                    Intent intent = new Intent(getActivity(), FirstContentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "خطا در دریافت توکن", Toast.LENGTH_SHORT).show();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<ResultJsonMemberSignUp> call, Throwable t) {

                Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

                btnConfirm.setEnabled(true);

            }

        });


    }

}
