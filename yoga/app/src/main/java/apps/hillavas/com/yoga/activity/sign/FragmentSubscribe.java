package apps.hillavas.com.yoga.activity.sign;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.data.models.OtpResultJson;
import apps.hillavas.com.yoga.data.models.ResultJson;
import apps.hillavas.com.yoga.data.models.SubscribeModel;
import apps.hillavas.com.yoga.data.remote.OtpApiFactory;
import apps.hillavas.com.yoga.factories.FragmentHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSubscribe extends Fragment implements View.OnClickListener {

    Button btnRegister;
    EditText editMobileNumber;
    ProgressBar progressBar;
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String GUID = "GUID";
    public static final String TRANSACTIONID = "TRANSACTIONID";
    public static final String SUBSCRIBEDUSER = "SubscribedUser";
    SharedPreferences sharedPreferencesHome;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscribe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnRegister = getActivity().findViewById(R.id.fragment_register_Btn_register);
        progressBar = getActivity().findViewById(R.id.progressRegister);
        editMobileNumber = getActivity().findViewById(R.id.txt_subscribe_number);

        btnRegister.setOnClickListener(this);

        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fragment_register_Btn_register:


//                if (!ConnectionChecker.check(getActivity())) {
//                    Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (editMobileNumber.length() < 11) {
                    Toast.makeText(getActivity(), R.string.errorMobileNumber, Toast.LENGTH_SHORT).show();
                    return;
                }

//todo
                //if(irrancell)else{}
                SubscribeModel subscribeModel = new SubscribeModel();
                subscribeModel.setMobileNumber(String.valueOf(editMobileNumber.getText()));

                btnRegister.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                OtpApiFactory.getOtpClient().subscribe(subscribeModel).enqueue(new Callback<OtpResultJson>() {
                    @Override
                    public void onResponse(Call<OtpResultJson> call, Response<OtpResultJson> response) {
                        if (response != null && response.body() != null) {

                            if (response.body().isIsSuccessfull() && response.body().getResult().equals("-1")) { //user is subscribed via phoneNumber

                                sharedPreferencesHome.edit().putLong(MOBILE_NUMBER, Long.valueOf(editMobileNumber.getText().toString())).commit();
                                sharedPreferencesHome.edit().putString(TRANSACTIONID, response.body().getMessage()).commit();
                                sharedPreferencesHome.edit().putBoolean(SUBSCRIBEDUSER, true).commit();

                                new FragmentHelper(new FragmentSubscribeConfirm(),
                                        R.id.frameLayout_base,
                                        getActivity().getSupportFragmentManager()
                                ).replace(true);


                            } else if (response.body().isIsSuccessfull()) {

                                sharedPreferencesHome.edit().putLong(MOBILE_NUMBER, Long.valueOf(editMobileNumber.getText().toString())).commit();
                                sharedPreferencesHome.edit().putString(TRANSACTIONID, response.body().getResult().toString()).commit();

                                new FragmentHelper(new FragmentSubscribeConfirm(),
                                        R.id.frameLayout_base,
                                        getActivity().getSupportFragmentManager()
                                ).replace(true);


                            } else {
                                 Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }

                        btnRegister.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onFailure(Call<OtpResultJson> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();
                        btnRegister.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                });

                break;

        }
    }
}
